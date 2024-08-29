# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class kr.co.are.searchcocktail.data.localroomcocktail.model.** {*;}
-keep class kr.co.are.searchcocktail.data.remoteapicocktail.model.** {*;}
-keep class kr.co.are.searchcocktail.data.remoteapistreamtext.model.** {*;}
-keep class kr.co.are.searchcocktail.data.localroomcocktail.entity.** {*;}
-keep class kr.co.are.searchcocktail.domain.entity.** {*;}

-keep class com.squareup.moshi.kotlin.reflect.** { *; }

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier interface *

-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}

-keepnames @com.squareup.moshi.JsonClass class *

-keepclasseswithmembers class **.*JsonAdapter extends com.squareup.moshi.JsonAdapter {
	<init>(...);
	<fields>;
}