# 工程简介
```properties
   本系列是学习java nio
```
# 延伸阅读
```properties
    学习一门新技术：个人理解-->问题驱动型，才是终点。
   疑问？
    1：java 不是有 bio 了么？为什么还会开发新的new io (NIO)
    2: nio 核心组件是哪些？分别对应的职责是什么?    
    3: nio 组件的使用于综合学习  
```
###抛出问题，不解决问题。那不就是耍流氓么？
**接下来就抛出代码，来解决实际问题**
##### bio 代码
```java
    public class BioServer extends Thread {

    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.start();
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(7397));
            System.out.println("itstack-demo-netty bio server start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
            while (true) {
                //注意这段代码。
                Socket socket = serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
**serverSocket.accept() 方法进去**
```java
     public Socket accept() throws IOException {
            if (isClosed())
                throw new SocketException("Socket is closed");
            if (!isBound())
                throw new SocketException("Socket is not bound yet");
            Socket s = new Socket((SocketImpl) null);
            implAccept(s);
            return s;
        }
```
**implAccept(s)**
```java
 protected final void implAccept(Socket s) throws IOException {
        ...
            getImpl().accept(si);
        ...
        
    }
```
**accept(SocketImpl s)**
```java
  protected void accept(SocketImpl s) throws IOException {

 
            socketAccept(s);
      
    }
```
**接着进去**
```java
  void socketAccept(SocketImpl s) throws IOException {
        if (timeout <= 0) {
            //阻塞
            newfd = accept0(nativefd, isaa);
        } else {
            configureBlocking(nativefd, false);
            try {
                waitForNewConnection(nativefd, timeout);
                 //阻塞
                newfd = accept0(nativefd, isaa);
                if (newfd != -1) {
                    configureBlocking(newfd, true);
                }
            } finally {
                configureBlocking(nativefd, true);
            }
        }
    }
```
>阻塞，阻塞在阻塞。
> Nio 采用reactor 模式
> 当链接过来，会创建channel 然后selector 会对channel 进行管理，通过byteBuffer 进行数据的读取
> 其余的就在代码出解释了
