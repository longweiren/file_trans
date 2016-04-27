###背景###
---
日常业务开发中常有这样的需求：客户上传批量数据的excel、csv文件；服务端读取文件并解析成对应的Java对象；然后业务处理，存数据库等等。

有不少代码都是这样处理：初始化一个业务数据对象object -> 读取第N行 -> 读取第N个单元格内容 -> 调用object的setter为数据对象赋值

不方便的地方是： 
``` 
1. 业务硬编码

2. excel数据解析和业务逻辑缠绕在一起

3. excel模板文件调整困难。当需求要调整excel模板文件时，需要修改java代码中的数据解析逻辑。当模板调整仅仅是列的顺序调整，也需要对业务代码做修改。
```

###使用###
```
示例1：
1. 定义模板文件。

第一行为定义行，每一单元格对应目标业务对象的属性。第2行开始为数据行。[/src/test/resources/userlist.xls]
  
2. 定义业务对象类型.

业务对象需要继承LineData.  
对需要把Excel单元格转换成对应值的属性定义 CellField注解，其中cellName对应Excel定义行中定义单元格的内容。匹配的定义单元格所在列数据会转换成业务对象的属性值。

3. 从输入流InputStream中读取并解析

    LineDataHandler<User> handler = null;
    LineDataValidator<User> validator = null;

    SheetLineProcessor<User> lineProcessor = new SheetLineProcessor<User>(validator, handler);

    SheetProcessor sheetProcessor = new SheetProcessor("user", User.class, lineProcessor);

    InputStream = new FileInputStream(new File("userlist.xls"));
    SheetProcessResult result = sheetProcessor.process(inStream, 1, true);

    上述示例代码会读取userlist.xsl并解析数据行为User对象，result包含解析结果。
```



