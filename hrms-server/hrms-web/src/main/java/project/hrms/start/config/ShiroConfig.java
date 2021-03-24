package project.hrms.start.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.hrms.start.realm.CustomerRealm;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //设置受限资源
        Map<String,String> map=new HashMap<>();
        //   /**表示出了login以外的资源全部受限
        map.put("/authority/login","anon"); //这个资源不需要认证
        map.put("/authority/register","anon"); //这个资源不需要认证
//        map.put("/js/**","anon"); //这个资源不需要认证
//        map.put("/register.jsp","anon");
//        map.put("/user/register","anon");
//        map.put("/index.jsp","authc");//authc请这个资源需要认证和授权
        //默认认证的界面路径 不写默认访问login.jsp做认证
        shiroFilterFactoryBean.setLoginUrl("login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomerRealm customerRealm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customerRealm);
        return defaultWebSecurityManager;
    }
    @Bean
    //自定义realm
    public CustomerRealm getReam(){
        //设置校验匹配器
        CustomerRealm customerRealm = new CustomerRealm();
        HashedCredentialsMatcher credentialsMatcher=new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//设置算法
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        return customerRealm;
    }
}
