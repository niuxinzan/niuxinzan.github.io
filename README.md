# sparkDemo

最近想升级spark，研究了下新版spark的功能Dataset 和Dataframe。
Dataset 是分布式数据集， dataset的API是在spark 1.6版本中添加地，它的初衷是为了提升RDD（强类型限制， 可以使用lambda函数）优化SQL执行引擎。Dataset是JVM中的一个对象，可以作用于其它操作（map,faltMap, filter等）。Dataset API提供Scala /Java 语言支持。 Python 暂不支持Dataset API， 但由于python的动态特性，已经支持 部分dataset API （例如， 用row.columnName来读取行数据的某列），这个例子和R语言的类似。
Dataframe 与dataset 基本类似 ，只是多了列名这些信息。 概念上，它类似于关系数据的表，及R/python 中的dataframe  但在执行效率上进一步优化。 可以从很多数据源中生成dataframe ， 如 结构化数据文件，hive表， 外部数据库，或 RDD。 Dataframe API支持 scala / java / python 和R 。在scala和java 中，dataframe  实际表示为dataset的行数据 。 在scala API 中，Dataframe 实际是Dataset[Row]的别名，但在java API 中， 开发人员需要使用Dataset<Row> 来表示 DataFrame 。 
使用dataframe的一般步骤；
1.定义SparkSession对象
2.读取文件，创建dataframe对象，提供了多种格式的数据读取方式，sql、hive、json、csv、text等
3.创建视图
4.编写sql语句，查询dataset
--------------------------------------------------
说明：
使用RDD的一般场景：
你需要使用low-level的transformation和action来控制你的数据集；
你的数据集非结构化，比如：流媒体或者文本流；
你想使用函数式编程来操作你的数据，而不是用特定领域语言(DSL)表达；
你不在乎schema，比如，当通过名字或者列处理(或访问)数据属性不在意列式存储格式；
你放弃使用DataFrame和Dataset来优化结构化和半结构化数据集。

使用DataFrame或者Dataset的一般场景:
你想使用丰富的语义，high-level抽象，和特定领域语言API，那你可以使用DataFrame或者Dataset；
你处理的半结构化数据集需要high-level表达，filter，map，aggregation，average，sum，SQL查询，列式访问和使用lambda函数，那你可以使用DataFrame或者Dataset；
你想利用编译时高度的type-safety，Catalyst优化和Tungsten的code生成，那你可以使用DataFrame或者Dataset；
你想统一和简化API使用跨Spark的Library，那你可以使用DataFrame或者Dataset；
如果你是一个R使用者，那你可以使用DataFrame或者Dataset；
如果你是一个Python使用者，那你可以使用DataFrame或者Dataset。
具体代码：https://github.com/niuxinzan/sparkDemo


