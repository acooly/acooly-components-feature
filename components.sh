#!/bin/bash


buildDoc(){
  dir=$1
  # 跳过当前目录和上级目录
  if [[ "$dir" == "." || "$dir" == ".." ]]; then
      exit 0
  fi
  readme_file="$dir/README.md"
  if [ -f "$readme_file" ]; then
    # 获取组件名称
    firstLine=$(head -n 1 "$readme_file")
    # 删除HTML注释开始和结束的标记，并打印剩余的内容
    filtered_text=${firstLine#<!-- title:}  # 删除开始标记
    filtered_text=${filtered_text%-->}  # 删除结束标记
    # 使用正则表达式去除两端的不可见字符
    filtered_text=$(echo "$filtered_text" | sed -e 's/^[[:space:]]*//' -e 's/[[:space:]]*$//')
    component_name=$filtered_text

    # 获取组件说明
    component_description=$(awk '/组件介绍/{flag=1;next} /##/{flag=0} flag' "$readme_file")
    while IFS= read -r line; do
      if [[ -n "$line" ]]; then
        component_description=$line
        break
      fi
    done <<< "$component_description"
    component_description=$(echo "$component_description" | awk 'NR==1{print}')
    echo "|[$(basename "$dir")](./${dir}README.md)|$component_name|$component_description|"
  fi
}



# 循环处理每个目录
for dir in */;
do
    readme_file="$dir/README.md"
    if [ -f "$readme_file" ]; then
        buildDoc "$dir"
    else
      for subdir in "$dir"*/;
      do
        buildDoc "$subdir"
      done
    fi
done
