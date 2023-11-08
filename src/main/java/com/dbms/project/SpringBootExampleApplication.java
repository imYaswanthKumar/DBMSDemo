package com.dbms.project;

import com.dbms.project.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExampleApplication implements CommandLineRunner {

	@Autowired
	private UserDao userdao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private ResumeDao resumeDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private WillingnessDao willingnessDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.userdao.CreateTable();
		this.branchDao.CreateTable();
		this.branchDao.insertAll();
		this.studentDao.CreateTable();
		this.companyDao.CreateTable();
		this.postDao.CreateTable();
		this.resumeDao.CreateTable();
		this.roleDao.CreateTable();
		this.willingnessDao.CreateTable();
		this.userdao.insertAdmin();
	}
}
