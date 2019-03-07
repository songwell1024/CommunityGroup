package com.communitygroup.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.communitygroup.user.dao.UserDao;
import com.communitygroup.user.pojo.User;
import com.communitygroup.user.util.ValidatorUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;


/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加, 密码加密
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		String password = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 发送验证码
	 * @param mobile
	 */
	public void sendSms(String mobile){

		if (!ValidatorUtil.isMobile(mobile)){
			throw  new IllegalArgumentException("手机号格式错误");
		}

//		//产生6位的随机数
//		int minValue = 100000;
//		int maxValue = 999999;
//		int randomValue = new Random().nextInt(maxValue);
//		if (randomValue < minValue){
//			randomValue += minValue;
//		}

		String randomValue = RandomStringUtils.randomNumeric(6);

		System.out.println("收到的验证码是：" + randomValue);
		//存入缓存做过期验证
		redisTemplate.opsForValue().set(mobile + "_" + (randomValue), randomValue, 10, TimeUnit.MINUTES);
		//放入消息对列，使用分裂模式实现消息的传递
		Map<String, String> map = new HashMap<>();
		map.put("mobile",mobile);
		map.put("checkcode", (randomValue));
		rabbitTemplate.convertAndSend("","sms",map );
	}

	/**
	 * 注册
	 * @param user
	 * @param code
	 */
	public void register(User user, String code) {
		String mobile = user.getMobile();

		if (redisTemplate.opsForValue().get(mobile + "_" + code) == null){
			throw new RuntimeException("请重新获取验证码");
		}
		if (!redisTemplate.opsForValue().get(mobile + "_" + code).equals(code)){
			throw new RuntimeException("验证码错误");
		}

		user.setId( idWorker.nextId()+"" );
		user.setFollowcount(0);//关注数
		user.setFanscount(0);//粉丝数
		user.setOnline(0L);//在线时长
		user.setRegdate(new Date());//注册日期
		user.setUpdatedate(new Date());//更新日期
		user.setLastdate(new Date());//最后登陆日期
		userDao.save(user);
	}

	/**
	 * 登录验证
	 * @param mobile
	 * @param password
	 * @return
	 */
	public User findByPasswordAndMobile(String mobile, String password){
		User user = userDao.findByMobile(mobile);
		if (user != null && bCryptPasswordEncoder.matches(password,user.getPassword())){
			return user;
		}else {
			return null;
		}
	}

	/**
	 * 更新粉丝数
	 * @param userid
	 * @param count
	 */
	public void updateFanscount(String userid, int count){
		userDao.updateFanscount(userid, count);
	}

	/**
	 * 更新关注数
	 * @param userid
	 * @param count
	 */
	public void updateFollowcount(String userid, int count){
		userDao.updateFollowcount(userid, count);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


}
