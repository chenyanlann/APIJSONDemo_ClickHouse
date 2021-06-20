# APIJSONDemo

APIJSON + SpringBoot连接ClickHouse使用的Demo

完成 *获取用户, 获取用户列表, 获取动态及发布者用户, 获取类似微信朋友圈的动态列表等* 功能测试

获取用户

![图片](https://user-images.githubusercontent.com/62465397/122662314-95aefb00-d1c4-11eb-80a9-86c7fc5d4f45.png)

获取用户列表

![图片](https://user-images.githubusercontent.com/62465397/122662335-c4c56c80-d1c4-11eb-9746-9d17738bc28c.png)

获取动态及发布者用户

![图片](https://user-images.githubusercontent.com/62465397/122662354-e7578580-d1c4-11eb-8270-6c0cf69fd7a2.png)

获取类似微信朋友圈的动态列表等

![图片](https://user-images.githubusercontent.com/62465397/122662372-0524ea80-d1c5-11eb-9582-2f0598387bac.png)


## 安装&使用
>JDK： 1.8+
>
>Maven： 3.0+
>
>数据库：ClickHouse
>
>JDBC 驱动:[ClickHouse Native JDBC](https://github.com/housepower/ClickHouse-Native-JDBC)
>
### 1.导入项目

IntelliJ IDEA导入：

顶部菜单File > Open > Browse

### 2.导入表文件到数据库，修改数据库配置

后端需要ClickHouse，安装之后可以使用clickhouse.sql脚本建表和导入数据。
[安装参考官方文档](https://clickhouse.tech/docs/zh/)

修改DemoSQLConfig源码，配置自己的数据库

### 运行

右键 DemoApplication > Run As > Java Application

SpringBoot默认端口8090

可以使用代码自带的数据库配置，但该用户只有只读权限

### 测试

完成获取用户，获取用户列表，获取动态及发布者用户，获取类似微信朋友圈的动态列表功能测试

#### 获取用户
请求：
<pre><code class="language-json">{
  "User":{
    "id":38710
  }
}
</code></pre>

[点击这里测试](http://localhost.cn:8090/get/{"User":{"id":38710}})

返回：
<pre><code class="language-json">{
    "User": {
        "id": 38710,
        "sex": 0,
        "name": "TommyLemon",
        "tag": "Android&Java",
        "head": "http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000",
        "contactIdList": "[82003, 82005, 90814, 82004, 82009, 82002, 82044, 93793, 70793]",
        "pictureList": "[\"http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000\", \"http://common.cnblogs.com/images/icon_weibo_24.png\"]",
        "date": "2017-02-01 11:21:50.0"
    },
    "ok": true,
    "code": 200,
    "msg": "success",
    "sql:generate|cache|execute|maxExecute": "1|0|1|200",
    "depth:count|max": "1|5",
    "time:start|duration|end": "1624163942343|119|1624163942462"
}
</code></pre>

<br />

#### 获取用户列表
请求：
<pre><code class="language-json">{
  "[]":{
    "count":3,             //只要3个
    "User":{
      "@column":"id,name"  //只要id,name这两个字段
    }
  }
}
</code></pre>

[点击这里测试](http://localhost:8090/get/{"[]":{"count":3,"User":{"@column":"id,name"}}})

返回：
<pre><code class="language-json">{
  "[]":[
    {
      "User":{
        "id":38710,
        "name":"TommyLemon"
      }
    },
    {
      "User":{
        "id":70793,
        "name":"Strong"
      }
    },
    {
      "User":{
        "id":82001,
        "name":"Android"
      }
    }
  ],
  "code":200,
  "msg":"success"
}
</code></pre>

<br />

#### 获取动态及发布者用户
请求：
<pre><code class="language-json">{
  "Moment":{
  },
  "User":{
    "id@":"Moment/userId"  //User.id = Moment.userId
  }
}
</code></pre>

[点击这里测试](http://apijson.cn:8090/get/{"Moment":{},"User":{"id@":"Moment%252FuserId"}})

返回：
<pre><code class="language-json">{
    "Moment": {
        "id": 12,
        "userId": 70793,
        "date": "2017-02-08 08:06:11.0",
        "content": "APIJSON,let interfaces and documents go to hell !",
        "praiseUserIdList": "[70793, 93793, 82044, 82040, 82055, 90814, 38710, 82002, 82006, 1508072105320, 82001]",
        "pictureList": "[\"http://static.oschina.net/uploads/img/201604/22172508_eGDi.jpg\", \"http://static.oschina.net/uploads/img/201604/22172507_rrZ5.jpg\", \"https://camo.githubusercontent.com/788c0a7e11a4f5aadef3c886f028c79b4808613a/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343932353935372d313732303737333630382e6a7067\", \"http://static.oschina.net/uploads/img/201604/22172507_Pz9Y.png\", \"https://camo.githubusercontent.com/c98b1c86af136745cc4626c6ece830f76de9ee83/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343930383036362d313837323233393236352e6a7067\", \"https://camo.githubusercontent.com/f513fa631bd780dc0ec3cf2663777e356dc3664f/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343733323232332d3337333933303233322e6a7067\", \"https://camo.githubusercontent.com/c98b1c86af136745cc4626c6ece830f76de9ee83/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343930383036362d313837323233393236352e6a7067\", \"https://camo.githubusercontent.com/f513fa631bd780dc0ec3cf2663777e356dc3664f/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f3636303036372f3230313630342f3636303036372d32303136303431343232343733323232332d3337333933303233322e6a7067\"]"
    },
    "User": {
        "id": 70793,
        "sex": 0,
        "name": "Strong",
        "tag": "djdj",
        "head": "http://static.oschina.net/uploads/user/585/1170143_50.jpg?t=1390226446000",
        "contactIdList": "[38710, 82002]",
        "pictureList": "[\"http://static.oschina.net/uploads/img/201604/22172508_eGDi.jpg\", \"http://static.oschina.net/uploads/img/201604/22172507_rrZ5.jpg\", \"https://camo.githubusercontent.com/788c0a7e11a\", \"https://camo.githubusercontent.com/f513f67\"]",
        "date": "2017-02-01 11:21:50.0"
    },
    "ok": true,
    "code": 200,
    "msg": "success",
    "sql:generate|cache|execute|maxExecute": "2|0|2|200",
    "depth:count|max": "1|5",
    "time:start|duration|end": "1624164089293|148|1624164089441"
}
</code></pre>

<br />

#### 获取类似微信朋友圈的动态列表
请求：
<pre><code class="language-json">{
  "[]":{                             //请求一个数组
    "page":0,                        //数组条件
    "count":2,
    "Moment":{                       //请求一个名为Moment的对象
      "content$":"%This%"               //对象条件，搜索content中包含a的动态
    },
    "User":{
      "id@":"/Moment/userId",        //User.id = Moment.userId  缺省引用赋值路径，从所处容器的父容器路径开始
      "@column":"id,name,head"       //指定返回字段
    },
    "Comment[]":{                    //请求一个名为Comment的数组，并去除Comment包装
      "count":2,
      "Comment":{
        "momentId@":"[]/Moment/id"   //Comment.momentId = Moment.id  完整引用赋值路径
      }
    }
  }
}
</code></pre>

[点击这里测试](http://apijson.cn:8090/get/{"[]":{"page":0,"count":2,"Moment":{"content$":"%2525This%2525"},"User":{"id@":"%252FMoment%252FuserId","@column":"id,name,head"},"Comment[]":{"count":2,"Comment":{"momentId@":"[]%252FMoment%252Fid"}}}})

返回：
<pre><code class="language-json">{
    "[]": [
        {
            "Moment": {
                "id": 58,
                "userId": 90814,
                "date": "2017-02-01 11:14:31.0",
                "content": "This is a Content...-435",
                "praiseUserIdList": "[38710, 82003, 82005, 93793, 82006, 82044, 82001]",
                "pictureList": "[\"http://static.oschina.net/uploads/img/201604/22172507_aMmH.jpg\"]"
            },
            "User": {
                "id": 90814,
                "name": "007",
                "head": "http://static.oschina.net/uploads/user/51/102723_50.jpg?t=1449212504000"
            },
            "Comment[]": [
                {
                    "id": 13,
                    "toId": 0,
                    "userId": 82005,
                    "momentId": 58,
                    "date": "2017-02-01 11:20:50.0",
                    "content": "This is a Content...-13"
                },
                {
                    "id": 77,
                    "toId": 13,
                    "userId": 93793,
                    "momentId": 58,
                    "date": "2017-02-01 11:20:50.0",
                    "content": "This is a Content...-77"
                }
            ]
        },
        {
            "Moment": {
                "id": 170,
                "userId": 70793,
                "date": "2017-02-01 11:14:31.0",
                "content": "This is a Content...-73",
                "praiseUserIdList": "[82044, 82002, 82001]",
                "pictureList": "[\"http://static.oschina.net/uploads/img/201604/22172508_eGDi.jpg\", \"http://static.oschina.net/uploads/img/201604/22172508_eGDi.jpg\", \"http://static.oschina.net/uploads/img/201604/22172508_mpwj.jpg\"]"
            },
            "User": {
                "id": 70793,
                "name": "Strong",
                "head": "http://static.oschina.net/uploads/user/585/1170143_50.jpg?t=1390226446000"
            },
            "Comment[]": [
                {
                    "id": 44,
                    "toId": 0,
                    "userId": 82003,
                    "momentId": 170,
                    "date": "2017-02-01 11:20:50.0",
                    "content": "This is a Content...-44"
                },
                {
                    "id": 54,
                    "toId": 0,
                    "userId": 82004,
                    "momentId": 170,
                    "date": "2017-02-01 11:20:50.0",
                    "content": "This is a Content...-54"
                }
            ]
        }
    ],
    "ok": true,
    "code": 200,
    "msg": "success",
    "sql:generate|cache|execute|maxExecute": "5|0|5|200",
    "depth:count|max": "5|5",
    "time:start|duration|end": "1624164139218|758|1624164139976"
}
</code></pre>
