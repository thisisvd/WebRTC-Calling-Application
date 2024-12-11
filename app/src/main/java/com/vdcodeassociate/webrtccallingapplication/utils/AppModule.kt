package com.vdcodeassociate.webrtccallingapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.vdcodeassociate.webrtccallingapplication.utils.Constants.SHARED_PREF_DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesContext(
        @ApplicationContext context: Context
    ) : Context = context.applicationContext

    @Provides
    fun providesGson() : Gson = Gson()

    @Provides
    fun providesDatabaseInstance() : FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun providesDatabaseReference(db : FirebaseDatabase) : DatabaseReference = db.reference

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_DATA, Context.MODE_PRIVATE)
    }
}