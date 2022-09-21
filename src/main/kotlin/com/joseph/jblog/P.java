//import com.joseph.jblog.entity.Role;
//import com.joseph.jblog.repository.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class P implements CommandLineRunner {
//
//    public static void main(String[] args) {
//        SpringApplication.run(P.class, args);
//    }
//
//    @Autowired
//    private RoleRepository roleRepository;
//    @Override
//    public void run(String... args) throws Exception {
//
//      Role adminRole = new Role();
//      adminRole.setName("ROLE_ADMIN");
//      roleRepository.save(adminRole);
//
//      Role userRole = new Role();
//      adminRole.setName("ROLE_USER");
//      roleRepository.save(adminRole);
//
//    }
//}
