# catalog-service

#### 命令

1. gradlew命令（默认在项目root目录下执行）
  ./gradlew test
  ./gradlew bootRun  启动项目
  # 使用Cloud Native Buildpacks将应用打包为镜像，避免编写Dockerfile
  ./gradlew bootBuildImage
  # 构建镜像后，同时发布到github容器注册中心，<token>替换为实际值
  ./gradlew bootBuildImage --imageName ghcr.io/duanyunxiang/catalog-service --publishImage -PregistryUsername=duanyunxiang -PregistryToken=<token>
  ./gradlew bootJar  将应用打包为jar文件，默认生成在build/libs/目录

  ./gradlew clean bootJar  构建jar制品，build/libs/

2. docker命令
  docker images  查看镜像详情
  docker run --rm --name catalog-service -p 8080:8080 catalog-service:0.0.1-SNAPSHOT  基于镜像运行容器，--rm 表示容器停止时自动删除容器，添加 -d 在后台运行
  docker pull postgres:14.4  手动拉取镜像
  # 在docker中启动PostgreSQL数据库
  docker run -d --name polar-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.4

  # 6.2节
  docker network create catalog-network  创建网络
  docker network ls  查看创建的网络
  docker network rm catalog-network  删除网络
  # 启动PostgreSQL数据库并声明网络
  docker run -d --name polar-postgres --net catalog-network -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.4
  docker build -t catalog-service .  基于Dockerfile构建容器镜像，在项目根目录执行，先确保jar制品已存在
  # 使用环境变量，指定容器中要使用的配置
  docker run -d --name catalog-service --net catalog-network -p 9001:9001 -e SPRING_DATASOURCE_URL=jdbc:postgresql://polar-postgres:5432/polardb_catalog -e SPRING_PROFILES_ACTIVE=testdata catalog-service
  docker rm -f catalog-service polar-postgres  删除容器

  # 6.3节
  docker-compose --version  检查docker compose版本
  docker-compose up -d  以detached模式启动容器，在docker-compose.yml所在目录运行

3. minikube机器ctl命令
  minikube delete  启动错误，可以尝试删除minikube再重建
  minikube start --driver=docker  启动minikube
  minikube stop
  minikube image load catalog-service:0.0.1-SNAPSHOT  将镜像导入本地集群
  docker context use default  提示 Unable to resolve the current Docker CLI context "default" 错误时执行

  kubectl version --client  查看kubectl版本
  kubectl get nodes  检查minikube是否正确启动
  kubectl config use-context minikube
  kubectl create deployment catalog-service --image=catalog-service:0.0.1-SNAPSHOT  指导k8s将应用创建为Pod，Deployment名为catalog-service
  kubectl get deployment  查看deployment
  kubectl get pod  查看pod
  kubectl expose deployment catalog-service --name=catalog-service --port=8080  将Catalog Service以Service资源形式暴露到集群中，Service名为catalog-service
  kubectl get service catalog-service  查看service创建情况
  kubectl port-forward service/catalog-service 8000:8080  将主机8000端口转发到集群中service的8080端口（使用ctrl+c终止端口转发）
  kubectl delete service catalog-service
  kubectl delete deployment catalog-service
