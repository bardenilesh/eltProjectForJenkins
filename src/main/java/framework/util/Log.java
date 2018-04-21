package framework.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log {

  static Logger log= Logger.getLogger(Log.class.getName());
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    BasicConfigurator.configure();
//    log.setLevel(Level.INFO);
    log.debug("Debug Message!");
    log.info("Info Message!");
    log.warn("Warn Message!");
  }

}
