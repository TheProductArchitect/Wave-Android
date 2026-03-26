package com.example.wave;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = WaveApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface WaveApplication_GeneratedInjector {
  void injectWaveApplication(WaveApplication waveApplication);
}
