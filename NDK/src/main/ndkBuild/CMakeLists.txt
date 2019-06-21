# 指定cmake最小支持版本
cmake_minimum_required(VERSION 3.4.1)
# 添加一个库，根据native-lib.cpp源文件编译一个native-lib的动态库
add_library(
        native-lib
        SHARED
        native-lib.cpp)
# 查找系统库，这里查找的是系统日志库，并赋值给变量log-lib
#find_library(
#        log-lib
#        log)
# 设置依赖的库（第一个参数必须为目标模块，顺序不能换）
target_link_libraries(
        native-lib
        ${log-lib})

# ---------------------------------------------------------------

# 变量
set(var 123)
message("var = ${var}")
# 列表
set(list_var 1 2 3 4)
message("list_var ${list_var}")
# 循环
set(a "")
while (NOT a STREQUAL "xxx")
    set(a "${a}x")
    message("a = ${a}")
endwhile ()
# 遍历 1
foreach (item 1 2 3)
    message("item = ${item}")
endforeach ()
# 遍历 2
foreach (i RANGE 4)
    message("i = ${i}")
endforeach ()
