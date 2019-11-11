package io.choerodon.devops.infra.util;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  14:58 2019/4/4
 * Description:
 */
public class PageRequestUtil {
    private PageRequestUtil() {
    }

    public static String checkSortIsEmpty(Pageable pageable) {
        String index = "";
        if (pageable.getSort() == null) {
            index = "true";
        }
        return index;
    }


    public static String getOrderBy(Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort != null) {
            return Lists.newArrayList(pageable.getSort().iterator()).stream()
                    .map(t -> t.getProperty() + " " + t.getDirection())
                    .collect(Collectors.joining(","));
        }
        return "";
    }

    public static String getOrderByStr(Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort != null) {
            return Lists.newArrayList(pageable.getSort().iterator()).stream()
                    .map(t -> t.getProperty() + "," + t.getDirection())
                    .collect(Collectors.joining(","));
        }
        return "";
    }

    public static String HumpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }
}
