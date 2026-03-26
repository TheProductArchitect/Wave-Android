package com.example.wave;

import com.example.wave.nfc.NfcManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<NfcManager> nfcManagerProvider;

  public MainActivity_MembersInjector(Provider<NfcManager> nfcManagerProvider) {
    this.nfcManagerProvider = nfcManagerProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<NfcManager> nfcManagerProvider) {
    return new MainActivity_MembersInjector(nfcManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectNfcManager(instance, nfcManagerProvider.get());
  }

  @InjectedFieldSignature("com.example.wave.MainActivity.nfcManager")
  public static void injectNfcManager(MainActivity instance, NfcManager nfcManager) {
    instance.nfcManager = nfcManager;
  }
}
