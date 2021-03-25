package project.hrms.start.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import project.hrms.start.entity.Employ;
import project.hrms.start.mapper.EmployMapper;
import project.hrms.start.service.EmployService;
import project.hrms.start.util.SpringFactoryUtil;

public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    SpringFactoryUtil factory;
    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
        return simpleAuthenticationInfo;
    }
    /**
     * 用户登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        EmployService service = factory.getContext().getBean("employService", EmployService.class);
        String principal = (String) authenticationToken.getPrincipal();
        long uid;
        Employ currentEmploy;
        try {
            uid = Long.parseLong(principal);
            currentEmploy = service.getEmployByUid(uid);
        }catch (Exception e){
            return null;
        }
        return currentEmploy == null?null:new SimpleAuthenticationInfo(principal,currentEmploy.getPassword(),this.getName());
    }
}
