
-keep class androidx.startup.** { *; }
-keep class androidx.lifecycle.** { *; }

-keep class kotlin.collections.** { *; }
-keep class kotlin.coroutines.** { *; }

-keep class dagger.hilt.** { *; }
-keep,allowobfuscation,allowshrinking @dagger.hilt.android.lifecycle.HiltViewModel class *

-keep class androidx.datastore.** { *; }