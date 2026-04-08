# Add project specific ProGuard rules here.
-keep class com.leon.kidspoints.data.local.entity.** { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
