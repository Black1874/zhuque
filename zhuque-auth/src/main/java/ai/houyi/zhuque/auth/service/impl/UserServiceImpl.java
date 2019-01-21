/*
 * Copyright 2017-2019 The OpenAds Project
 *
 * The OpenAds Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package ai.houyi.zhuque.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.houyi.zhuque.auth.model.ChangePwdReq;
import ai.houyi.zhuque.auth.model.ResetPasswdReq;
import ai.houyi.zhuque.auth.service.UserService;
import ai.houyi.zhuque.commons.exception.ZhuqueException;
import ai.houyi.zhuque.commons.page.Page;
import ai.houyi.zhuque.core.model.query.UserQueryReq;
import ai.houyi.zhuque.dao.PermissionMapper;
import ai.houyi.zhuque.dao.RoleMapper;
import ai.houyi.zhuque.dao.RolePermissionMapper;
import ai.houyi.zhuque.dao.UserMapper;
import ai.houyi.zhuque.dao.UserRoleMapper;
import ai.houyi.zhuque.dao.model.Permission;
import ai.houyi.zhuque.dao.model.PermissionExample;
import ai.houyi.zhuque.dao.model.Role;
import ai.houyi.zhuque.dao.model.RoleExample;
import ai.houyi.zhuque.dao.model.RolePermissionExample;
import ai.houyi.zhuque.dao.model.User;
import ai.houyi.zhuque.dao.model.UserExample;
import ai.houyi.zhuque.dao.model.UserRole;
import ai.houyi.zhuque.dao.model.UserRoleExample;

/**
 * @author weiping wang
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public void save(User t) {
		userMapper.insertSelective(t);
	}

	@Override
	public void update(User t) {
		userMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Integer pk) {
		userRoleMapper.deleteByExample(new UserRoleExample().createCriteria().andUserIdEqualTo(pk).example());
		userMapper.deleteByPrimaryKey(pk);
	}

	@Override
	public void softDeleteById(Integer pk) {
		throw new UnsupportedOperationException("用户不支持软删除");
	}

	@Override
	public User loadById(Integer pk) {
		return userMapper.selectByPrimaryKey(pk);
	}

	@Override
	public List<User> selectAll() {
		throw new UnsupportedOperationException("unsupported selectAll operation for user");
	}

	@Override
	public List<User> selectByQueryReq(UserQueryReq queryReq) {
		return userMapper.selectByExample(queryReq.toExample());
	}

	@Override
	public Page<User> selectPageList(UserQueryReq queryReq) {
		UserExample example = queryReq.toExample();
		int total = (int) userMapper.countByExample(example);
		List<User> result = userMapper.selectByExample(example);

		return Page.create(total, queryReq.getPageSize(), result);
	}

	@Override
	public void resetPasswd(ResetPasswdReq req) {
		User user = new User();
		user.setId(req.getUserId());
		user.setPasswd(DigestUtils.md5Hex(req.getPasswd()));

		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void updatePasswd(ChangePwdReq req) {
		UserExample example = new UserExample();
		example.createCriteria().andIdEqualTo(req.getUserId()).andPasswdEqualTo(DigestUtils.md5Hex(req.getOldPasswd()));

		boolean exists = userMapper.countByExample(example) > 0;
		if (!exists)
			throw new ZhuqueException("用户不存在或者密码不正确");

		User user = new User();
		user.setId(req.getUserId());
		user.setPasswd(DigestUtils.md5Hex(req.getPasswd()));

		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	@Transactional
	public void updateUserRoles(Integer userId, List<Integer> roleIds) {
		UserRoleExample example = new UserRoleExample().createCriteria().andUserIdEqualTo(userId).example();
		userRoleMapper.deleteByExample(example);

		if (roleIds == null)
			return;
		List<UserRole> userRoles = new ArrayList<UserRole>();
		for (Integer roleId : roleIds) {
			userRoles.add(new UserRole.Builder().roleId(roleId).userId(userId).build());
		}
		userRoleMapper.batchInsert(userRoles);
	}

	@Override
	public List<Role> getUserRoles(Integer userId) {
		UserRoleExample example = new UserRoleExample().createCriteria().andUserIdEqualTo(userId).example();
		List<UserRole> userRoles = userRoleMapper.selectByExample(example);

		if (userRoles == null || userRoles.isEmpty())
			return null;

		List<Integer> roleIds = new ArrayList<Integer>();
		userRoles.forEach(ur -> roleIds.add(ur.getRoleId()));
		if (roleIds.isEmpty())
			return null;

		return roleMapper.selectByExample(new RoleExample().createCriteria().andIdIn(roleIds).example());
	}

	@Override
	public List<Permission> getUserPermissions(Integer userId) {
		UserRoleExample example = new UserRoleExample().createCriteria().andUserIdEqualTo(userId).example();
		List<UserRole> userRoles = userRoleMapper.selectByExample(example);

		List<Integer> roleIds = userRoles.stream().map(ur -> ur.getRoleId()).collect(Collectors.toList());
		if (roleIds == null || roleIds.isEmpty())
			return null;

		RolePermissionExample _example = new RolePermissionExample().createCriteria().andRoleIdIn(roleIds).example();
		List<Integer> permissionIds = rolePermissionMapper.selectByExample(_example).stream()
				.map(rp -> rp.getPermissionId()).collect(Collectors.toList());

		if (permissionIds == null || permissionIds.isEmpty())
			return null;
		return permissionMapper
				.selectByExample(PermissionExample.newAndCreateCriteria().andIdIn(permissionIds).example());
	}
}
