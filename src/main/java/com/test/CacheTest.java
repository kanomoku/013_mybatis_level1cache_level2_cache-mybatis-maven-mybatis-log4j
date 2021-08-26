package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.pojo.LogInfo;

public class CacheTest {
//	public static void main(String[] args) throws IOException {

	@Test
	public void test1() throws IOException {

		InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

//		1. 应用程序和数据库交互的过程是一个相对比较耗时的过程
//		2. 缓存存在的意义:让应用程序减少对数据库的访问,提升程序运行效率
//		3. MyBatis 中默认SqlSession 缓存开启
//			3.1 同一个SqlSession 对象调用同一个<select>时,只有第一次访问数据库,第一次之后把查询结果缓存到SqlSession 缓存区(内存)中
//			3.2 缓存的是statement 对象.(简单记忆必须是用一个<select>)
//				3.2.1 在myabtis 时一个<select>对应一个statement 对象
//			3.3 有效范围必须是同一个SqlSession 对象
		SqlSession session = factory.openSession();
		List<LogInfo> selectList = session.selectList("com.mapper.LogMapper.selAll");
		for (LogInfo logInfo : selectList) {
//			System.out.println(logInfo);
		}
		List<LogInfo> selectList1 = session.selectList("com.mapper.LogMapper.selAll1");
		for (LogInfo logInfo : selectList1) {
//			System.out.println(logInfo);
		}
		SqlSession session1 = factory.openSession();
		List<LogInfo> selectList11 = session1.selectList("com.mapper.LogMapper.selAll1");

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		4. 缓存流程
//			4.1 步骤一: 先去缓存区中找是否存在statement
//			4.2 步骤二:返回结果
//			4.3 步骤三:如果没有缓存statement 对象,去数据库获取数据
//			4.4 步骤四:数据库返回查询结果
//			4.5 步骤五:把查询结果放到对应的缓存区中
		List<LogInfo> selectList2 = session.selectList("com.mapper.LogMapper.selAll2");// 两次查询只有一个sql
		for (LogInfo logInfo : selectList2) {
//			System.out.println(logInfo);
		}
		List<LogInfo> selectList3 = session.selectList("com.mapper.LogMapper.selAll2");// 两次查询只有一个sql
		for (LogInfo logInfo : selectList3) {
//			System.out.println(logInfo);
		}
		session.close();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test2() throws IOException {
//		5. SqlSessionFactory 缓存
//			5.1 又叫:二级缓存
//			5.2 有效范围:同一个factory 内哪个SqlSession 都可以获取
//			5.3 什么时候使用二级缓存:
//				5.3.1 当数据频繁被使用,很少被修改
//			5.4 使用二级缓存步骤
//				5.4.1 在mapper.xml 中添加<cache readOnly="true"></cache>
//				5.4.2 如果不写readOnly=”true”需要把实体类序列化
//			5.5 当SqlSession 对象close()时或commit()时会把SqlSession 缓存的数据刷(flush)到SqlSessionFactory 缓存区中

		InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session2 = factory.openSession();
		List<LogInfo> session2selectList2 = session2.selectList("com.mapper.LogMapper.selAll1");
		for (LogInfo object : session2selectList2) {
			// System.out.println(object); }
		}

		session2.close();// 注意这里不关闭 以及缓存不会往二级缓存里面存
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		SqlSession session3 = factory.openSession();
		List<LogInfo> session3selectList3 = session3.selectList("com.mapper.LogMapper.selAll1");
		for (LogInfo object : session3selectList3) {
			// System.out.println(object); } session3.close();
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		SqlSession session4 = factory.openSession();
		List<LogInfo> session4selectList4 = session4.selectList("com.mapper.LogMapper.selAll2");
		for (LogInfo object : session4selectList4) {
			// System.out.println(object); } session4.close();
		}
	}

}
