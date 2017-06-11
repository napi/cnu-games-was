package kr.ac.cnu.aspect;

import kr.ac.cnu.annotation.AesEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rokim on 2017. 6. 12..
 */
@Slf4j
@Aspect
@Component
public class AesAspect {
    @Around("execution(* kr.ac.cnu.repository.*.* (..))")
    public Object encry(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AesEncrypt methodAnnotation = signature.getMethod().getAnnotation(AesEncrypt.class);

		/* Parameter 암호화 */
        // 파라미터 AesEncrypt Annotation을 찾는다.
        Annotation[][] parameterAnnos = signature.getMethod().getParameterAnnotations();
        List<Integer> annoIndexList = new ArrayList<Integer>();
        for (int i = 0 ; i < parameterAnnos.length ; i++) {
            for (Annotation anno : parameterAnnos[i]) {
                if (anno instanceof AesEncrypt) {
                    annoIndexList.add(i);
                }
            }
        }

        Object[] objs = joinPoint.getArgs();

        for (int i = 0 ; i < objs.length ; i++) {
            Object obj = objs[i];

            if (obj == null) {
                continue;
            }

            // Method의 @AesEncrypt 파라미터 암호화 (String type 만 처리)
            if (obj instanceof String) {
                if (annoIndexList.contains(i)) {
                    objs[i] = CipherHelper.encryptHexAES128((String)obj);
                }
                continue;
            }

            // Model의 @AesEncrypt 필드 암호화
            encryptModel(obj);
        }
		/* // Parameter 암호화 */

        // joinPoint Proceed
        Object result = joinPoint.proceed(objs);

		/* Result 복호화 */
        if (result != null) {
            // Method의 @AesEncrypt의 Return 을 복호화 (String type 만 처리)
            if (methodAnnotation != null && result instanceof String) {
                result = CipherHelper.encryptHexAES128((String)result);
            } else if (result instanceof Iterable) {
                Iterator<?> itor = ((Iterable<?>)result).iterator();
                while(itor.hasNext()) {
                    Object obj = itor.next();
                    decryptModel(obj);
                }
            } else {
                // Return Model의 @AesEncrypt 필드 복호화
                decryptModel(result);
            }
        }
		/* // Result 복호화 */

        return result;
    }

    private void encryptModel(Object obj) throws Throwable {
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(AesEncrypt.class)) {
                f.setAccessible(true);
                Object value = null;
                try {
                    value = f.get(obj);
                    if (value instanceof String) {
                        f.set(obj, CipherHelper.encryptHexAES128((String)value));
                    }
                } catch (IllegalArgumentException
                        | IllegalAccessException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    private void decryptModel(Object obj) throws Throwable{
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(AesEncrypt.class)) {
                f.setAccessible(true);

                Object value = null;
                try {
                    value = f.get(obj);

                    if (value instanceof String) {
                        f.set(obj, CipherHelper.decryptHexAES128((String)value));
                    }
                } catch (IllegalArgumentException
                        | IllegalAccessException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }
}
