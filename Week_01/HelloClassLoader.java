import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

**
 * @Description: 第一课 题目二
 * @Author: qinxu
 * @Date: 2021-01-15 15:00
 */
public class HelloClassLoader extends ClassLoader{
	
    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");
            Method method = clazz.getDeclaredMethod("hello");
            method.invoke(clazz.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        
        byte[] xlassBytes;
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(className + ".xlass");
            ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
            int nextValue;
            while ((nextValue = inputStream.read()) != -1) {
                byteOutPut.write(255 - nextValue);
            }
            xlassBytes =  byteOutPut.toByteArray();
            } catch (IOException e) {
                System.err.println("error ocourred when HelloClassLoader.loadClassData");
                e.printStackTrace();
                throw new ClassNotFoundException("xlass file not found");
        }
        return defineClass(className, xlassBytes, 0, xlassBytes.length);
    }
    
}