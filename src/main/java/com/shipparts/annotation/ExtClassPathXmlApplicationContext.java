package com.shipparts.annotation;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtClassPathXmlApplicationContext {
    //扫包范围
    private String packageName;

    private ConcurrentHashMap<String, Object> beans = null;

    public ExtClassPathXmlApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<String, Object>();
        this.packageName = packageName;
        initBean();
        //初始化属性
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object bean = entry.getValue();
            attriAssign(bean);
        }
    }

    /**
     * 初始化对象
     *
     * @throws Exception
     */
    public void initBean() throws Exception {
        List<Class> classes = ClassUtils.getClasses(packageName);
        ConcurrentHashMap<String, Object> classExistAnnotation = findClassExistAnnotation(classes);
        if (classExistAnnotation == null || classExistAnnotation.isEmpty()) {
            throw new Exception("该包下任何类没有注解！");
        }
    }

    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId不能为空！");
        }
        //1.从spring容器获取bean
        Object object = beans.get(beanId);
        /*if (object == null) {
            throw new Exception("class not found");
        }*/
        //2.反射机制初始化
        return object;
    }

    public Object newInstance(Class<?> classInfo) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    public ConcurrentHashMap<String, Object> findClassExistAnnotation(List<Class> classes) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for (Class classInfo : classes) {
            ExtService annotation = (ExtService) classInfo.getAnnotation(ExtService.class);
            if (annotation != null) {
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                Object object = newInstance(classInfo);
                beans.put(beanId, object);
                continue;
            }
        }
        return beans;
    }


    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
    }

    public void attriAssign(Object object) throws Exception {
        //1.反射机制，获取当前类属性
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();
        //2.判断当前类属性是否有注解
        for (Field field : declaredFields) {
            ExtResource extResource = field.getAnnotation(ExtResource.class);
            if (extResource != null) {
                //获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    field.setAccessible(true);
                    field.set(object, bean);
                }
            }
        }

    }
}
