# docker
1. -p 9999:9999 9998:5005
2. -v /root:/root/file
```docker
docker run \
--name=file-server \
-itd \
-p 9999:9999 \
-p 9998:5005 \
-v /root:/root/file \
file-server