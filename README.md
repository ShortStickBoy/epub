# epub

### ProGuard

```groovy
-dontwarn org.dom4j.**
-keep class org.dom4j.** { *; }

-dontwarn com.sunzn.epub.library.**
-keep class com.sunzn.epub.library.** { *; }
```