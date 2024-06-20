# Build
custom_build(
    # Name of the container image
    ref = 'catalog-service',
    # On Windows, replace $EXPECTED_REF with %EXPECTED_REF%
    # ./gradlew会报错，去掉./
    command = 'gradlew bootBuildImage --imageName %EXPECTED_REF%',
    # Files to watch that trigger a new build
    deps = ['build.gradle', 'src']
)

# 使用KustomizeDeploy
k8s_yaml(kustomize('k8s'))

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])