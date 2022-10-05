package sql.tool;



import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class ReadSQL {

public static void main(String[] args) {
	
}
public static List<Class> getClasssFromPackage(String pack) {
	  List<Class> clazzs = new ArrayList<Class>();

	  // 是否循環搜索子包
	  boolean recursive = true;

	  // 包名字
	  String packageName = pack;
	  // 包名對應的路徑名稱
	  String packageDirName = packageName.replace('.', '/');

	  Enumeration<URL> dirs;

	  try {
	    dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
	    while (dirs.hasMoreElements()) {
	      URL url = dirs.nextElement();

	      String protocol = url.getProtocol();

	      if ("file".equals(protocol)) {
	        System.out.println("file類型的掃描");
	        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
	        findClassInPackageByFile(packageName, filePath, recursive, clazzs);
	      } else if ("jar".equals(protocol)) {
	        System.out.println("jar類型的掃描");
	      }
	    }

	  } catch (Exception e) {
	    e.printStackTrace();
	  }

	  return clazzs;
	}
/**
 * 在package對應的路徑下找到所有的class
 * 
 * @param packageName
 *            package名稱
 * @param filePath
 *            package對應的路徑
 * @param recursive
 *            是否查找子package
 * @param clazzs
 *            找到class以後存放的集合
 */
public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class> clazzs) {
  File dir = new File(filePath);
  if (!dir.exists() || !dir.isDirectory()) {
    return;
  }
  // 在給定的目錄下找到所有的文件，並且進行條件過濾
  File[] dirFiles = dir.listFiles(new FileFilter() {

    @Override
    public boolean accept(File file) {
      boolean acceptDir = recursive && file.isDirectory();// 接受dir目錄
      boolean acceptClass = file.getName().endsWith("class");// 接受class文件
      return acceptDir || acceptClass;
    }
  });

  for (File file : dirFiles) {
    if (file.isDirectory()) {
      findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
    } else {
      String className = file.getName().substring(0, file.getName().length() - 6);
      try {
        clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
}