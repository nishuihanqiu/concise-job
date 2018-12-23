package com.lls.api.concise.annotation;

import java.lang.annotation.*;

/************************************
 * Nullable
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Nullable {

}
