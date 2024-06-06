# catalog-service

#### 介绍
{**以下是 Gitee 平台说明，您可以替换此简介**
Gitee 是 OSCHINA 推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用 Gitee 实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1. gradlew命令（默认在项目root目录下执行）
  ./gradlew test
  ./gradlew bootRun 启动项目
  ./gradlew bootBuildImage 使用Cloud Native Buildpacks将应用打包为镜像，避免编写Dockerfile
  ./gradlew bootJar 将应用打包为jar文件，默认生成在build/libs/目录

2. docker命令
  docker images 查看镜像详情
  docker run --rm --name catalog-service -p 8080:8080 catalog-service:0.0.1-SNAPSHOT 基于镜像运行容器，--rm 表示容器停止时自动删除容器，添加 -d 在后台运行

3. minikube机器ctl命令
  minikube delete 启动错误，可以尝试删除minikube再重建
  minikube start --driver=docker 启动minikube
  minikube stop
  minikube image load catalog-service:0.0.1-SNAPSHOT 将镜像导入本地集群
    docker context use default 提示 Unable to resolve the current Docker CLI context "default" 错误时执行

  kubectl version --client 查看kubectl版本
  kubectl get nodes 检查minikube是否正确启动
  kubectl config use-context minikube
  kubectl create deployment catalog-service --image=catalog-service:0.0.1-SNAPSHOT 指导k8s将应用创建为Pod，Deployment名为catalog-service
  kubectl get deployment 查看deployment
  kubectl get pod 查看pod
  kubectl expose deployment catalog-service --name=catalog-service --port=8080 将Catalog Service以Service资源形式暴露到集群中，Service名为catalog-service
  kubectl get service catalog-service 查看service创建情况
  kubectl port-forward service/catalog-service 8000:8080 将主机8000端口转发到集群中service的8080端口（使用ctrl+c终止端口转发）
  kubectl delete service catalog-service
  kubectl delete deployment catalog-service

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
