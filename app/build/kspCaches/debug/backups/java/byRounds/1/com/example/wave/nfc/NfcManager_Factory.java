package com.example.wave.nfc;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class NfcManager_Factory implements Factory<NfcManager> {
  @Override
  public NfcManager get() {
    return newInstance();
  }

  public static NfcManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NfcManager newInstance() {
    return new NfcManager();
  }

  private static final class InstanceHolder {
    private static final NfcManager_Factory INSTANCE = new NfcManager_Factory();
  }
}
