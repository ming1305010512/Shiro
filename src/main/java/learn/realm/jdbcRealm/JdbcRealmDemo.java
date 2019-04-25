package learn.realm.jdbcRealm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @Created with IDEA
 * @author:longming
 * @Date:2019/4/24
 * @Time:17:14
 * @Description 测试shiro的第一个案例
 */
public class JdbcRealmDemo {

    public static void main(String[] args) {
        /*实例化工厂*/
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("C:\\学习\\项目\\学习项目\\apache-shiro-tutorial-webapp\\src\\main\\webapp\\WEB-INF\\shiro.ini");
        /*获取实例*/
        SecurityManager securityManager = factory.getInstance();
        /*设置参数*/
        SecurityUtils.setSecurityManager(securityManager);
        /*获取登录实例*/
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            /*实例化token,传入登录的用户和密码*/
            UsernamePasswordToken token = new UsernamePasswordToken("zhao", "123456");

            try {
                /*进行登录*/
                subject.login(token);
                System.out.println("登录成功！！");
                System.out.println("用户名："+subject.getPrincipal());
                System.out.println(subject.getPrincipals());
                if (subject.hasRole("admin")){
                    System.out.println("拥有该角色");
                }else {
                    System.out.println("没有该角色");
                }

                if (subject.isPermitted("user:delete")){
                    System.out.println("拥有该权限");
                }else {
                    System.out.println("没有该权限");
                }
                /*退出登录*/
                subject.logout();
            }catch (Exception e){
                System.out.println("登录错误！！");
            }
        }
    }
}
