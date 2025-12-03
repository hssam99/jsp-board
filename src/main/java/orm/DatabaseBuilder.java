package orm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseBuilder {

    private static SqlSessionFactory factory;
    private static final String CONFIG = "mybatis-config.xml";

    static {
        try{
            System.out.println("=== MyBatis 설정 시작 ===");
            InputStream inputStream = Resources.getResourceAsStream(CONFIG);

            if(inputStream == null) {
                System.out.println("❌ 파일을 찾을 수 없음: " + CONFIG);
            } else {
                System.out.println("✅ 파일 찾음: " + CONFIG);
                factory = new SqlSessionFactoryBuilder().build(inputStream);
                System.out.println("✅ SqlSessionFactory 생성 완료");
            }
        } catch (Exception e){
            System.out.println("❌ 에러 발생:");
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getFactory(){
        return factory;
    }
}