package top.zhenyuan.jidb.backend.tbm;

import top.zhenyuan.jidb.backend.dm.DataManager;
import top.zhenyuan.jidb.backend.parser.statement.Begin;
import top.zhenyuan.jidb.backend.parser.statement.Create;
import top.zhenyuan.jidb.backend.parser.statement.Delete;
import top.zhenyuan.jidb.backend.parser.statement.Insert;
import top.zhenyuan.jidb.backend.parser.statement.Select;
import top.zhenyuan.jidb.backend.parser.statement.Update;
import top.zhenyuan.jidb.backend.utils.Parser;
import top.zhenyuan.jidb.backend.vm.VersionManager;

public interface TableManager {
    BeginRes begin(Begin begin);
    byte[] commit(long xid) throws Exception;
    byte[] abort(long xid);

    byte[] show(long xid);
    byte[] create(long xid, Create create) throws Exception;

    byte[] insert(long xid, Insert insert) throws Exception;
    byte[] read(long xid, Select select) throws Exception;
    byte[] update(long xid, Update update) throws Exception;
    byte[] delete(long xid, Delete delete) throws Exception;

    public static TableManager create(String path, VersionManager vm, DataManager dm) {
        Booter booter = Booter.create(path);
        booter.update(Parser.long2Byte(0));
        return new TableManagerImpl(vm, dm, booter);
    }

    public static TableManager open(String path, VersionManager vm, DataManager dm) {
        Booter booter = Booter.open(path);
        return new TableManagerImpl(vm, dm, booter);
    }
}
