package com.example.wave;

import com.example.wave.nfc.NfcManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class WaveViewModel_Factory implements Factory<WaveViewModel> {
  private final Provider<NfcManager> nfcManagerProvider;

  public WaveViewModel_Factory(Provider<NfcManager> nfcManagerProvider) {
    this.nfcManagerProvider = nfcManagerProvider;
  }

  @Override
  public WaveViewModel get() {
    return newInstance(nfcManagerProvider.get());
  }

  public static WaveViewModel_Factory create(Provider<NfcManager> nfcManagerProvider) {
    return new WaveViewModel_Factory(nfcManagerProvider);
  }

  public static WaveViewModel newInstance(NfcManager nfcManager) {
    return new WaveViewModel(nfcManager);
  }
}
