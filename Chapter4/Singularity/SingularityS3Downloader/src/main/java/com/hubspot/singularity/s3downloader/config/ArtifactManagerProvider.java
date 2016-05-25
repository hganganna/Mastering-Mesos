package com.hubspot.singularity.s3downloader.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.hubspot.singularity.runner.base.sentry.SingularityRunnerExceptionNotifier;
import com.hubspot.singularity.s3.base.ArtifactManager;
import com.hubspot.singularity.s3.base.config.SingularityS3Configuration;

public class ArtifactManagerProvider implements Provider<ArtifactManager> {

  private final Logger log;
  private final SingularityS3Configuration s3Configuration;
  private final SingularityRunnerExceptionNotifier exceptionNotifier;

  @Inject
  public ArtifactManagerProvider(SingularityS3Configuration s3Configuration, SingularityRunnerExceptionNotifier exceptionNotifier) {
    this.log = LoggerFactory.getLogger(ArtifactManager.class);
    this.s3Configuration = s3Configuration;
    this.exceptionNotifier = exceptionNotifier;
  }

  @Override
  public ArtifactManager get() {
    return new ArtifactManager(s3Configuration, log, exceptionNotifier);
  }

}
