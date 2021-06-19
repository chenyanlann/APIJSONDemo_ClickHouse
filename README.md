# APIJSONDemo

APIJSON + SpringBoot连接ClickHouse使用的Demo

完成 *获取用户, 获取用户列表, 获取动态及发布者用户, 获取类似微信朋友圈的动态列表等* 功能测试

获取用户

![图片](https://user-images.githubusercontent.com/62465397/122654263-de42c600-d17c-11eb-880e-731038e5950e.png)

获取用户列表

![图片](https://user-images.githubusercontent.com/62465397/122654278-f74b7700-d17c-11eb-9bdb-5523042093c3.png)

获取动态及发布者用户

![图片](https://user-images.githubusercontent.com/62465397/122654397-a8eaa800-d17d-11eb-90ea-3dbc82962121.png)
![图片](https://user-images.githubusercontent.com/62465397/122654401-b6079700-d17d-11eb-8897-0d018e40a9b7.png)

获取类似微信朋友圈的动态列表等

![图片](https://user-images.githubusercontent.com/62465397/122654541-c409e780-d17e-11eb-81aa-e47fbb680dd7.png)



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

后端需要ClickHouse，安装之后可以使用clickhouse_all2.sql脚本建表和导入数据。
[安装参考官方文档](https://clickhouse.tech/docs/zh/)

修改DemoSQLConfig源码，配置自己的数据库

### 运行

右键 DemoApplication > Run As > Java Application

SpringBoot默认端口8090

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
  "User":{
    "id":38710,
    "sex":0,
    "name":"TommyLemon",
    "tag":"Android&Java",
    "head":"http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000",
    "date":1485948110000,
    "pictureList":[
      "http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000",
      "http://common.cnblogs.com/images/icon_weibo_24.png"
    ]
  },
  "code":200,
  "msg":"success"
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
  "Moment":{
    "id":12,
    "userId":70793,
    "date":"2017-02-08 16:06:11.0",
    "content":"1111534034"
  },
  "User":{
    "id":70793,
    "sex":0,
    "name":"Strong",
    "tag":"djdj",
    "head":"http://static.oschina.net/uploads/user/585/1170143_50.jpg?t=1390226446000",
    "contactIdList":[
      38710,
      82002
    ],
    "date":"2017-02-01 19:21:50.0"
  },
  "code":200,
  "msg":"success"
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
      "content$":"%a%"               //对象条件，搜索content中包含a的动态
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

[点击这里测试](http://apijson.cn:8090/get/{"[]":{"page":0,"count":2,"Moment":{"content$":"%2525a%2525"},"User":{"id@":"%252FMoment%252FuserId","@column":"id,name,head"},"Comment[]":{"count":2,"Comment":{"momentId@":"[]%252FMoment%252Fid"}}}})

返回：
<pre><code class="language-json">{
  "[]":[
    {
      "Moment":{
        "id":15,
        "userId":70793,
        "date":1486541171000,
        "content":"APIJSON is a JSON Transmission Structure Protocol…",
        "praiseUserIdList":[
          82055,
          82002,
          82001
        ],
        "pictureList":[
          "http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000",
          "http://common.cnblogs.com/images/icon_weibo_24.png"
        ]
      },
      "User":{
        "id":70793,
        "name":"Strong",
        "head":"http://static.oschina.net/uploads/user/585/1170143_50.jpg?t=1390226446000"
      },
      "Comment[]":[
        {
          "id":176,
          "toId":166,
          "userId":38710,
          "momentId":15,
          "date":1490444883000,
          "content":"thank you"
        },
        {
          "id":1490863469638,
          "toId":0,
          "userId":82002,
          "momentId":15,
          "date":1490863469000,
          "content":"Just do it"
        }
      ]
    },
    {
      "Moment":{
        "id":58,
        "userId":90814,
        "date":1485947671000,
        "content":"This is a Content...-435",
        "praiseUserIdList":[
          38710,
          82003,
          82005,
          93793,
          82006,
          82044,
          82001
        ],
        "pictureList":[
          "http://static.oschina.net/uploads/img/201604/22172507_aMmH.jpg"
        ]
      },
      "User":{
        "id":90814,
        "name":7,
        "head":"http://static.oschina.net/uploads/user/51/102723_50.jpg?t=1449212504000"
      },
      "Comment[]":[
        {
          "id":13,
          "toId":0,
          "userId":82005,
          "momentId":58,
          "date":1485948050000,
          "content":"This is a Content...-13"
        },
        {
          "id":77,
          "toId":13,
          "userId":93793,
          "momentId":58,
          "date":1485948050000,
          "content":"This is a Content...-77"
        }
      ]
    }
  ],
  "code":200,
  "msg":"success"
}
</code></pre>
