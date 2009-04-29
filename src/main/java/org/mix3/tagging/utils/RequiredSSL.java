package org.mix3.tagging.utils;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited //For a "BasePage" strategy
public @interface RequiredSSL { }