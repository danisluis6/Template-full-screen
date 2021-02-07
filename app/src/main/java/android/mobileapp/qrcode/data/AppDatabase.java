package android.mobileapp.qrcode.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.mobileapp.qrcode.data.storage.dao.ContentDAO;
import android.mobileapp.qrcode.data.storage.entities.Content;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Database(entities = {Content.class}, version = DatabaseInfo.DB_VERSION, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = DatabaseInfo.DB_NAME;

    public abstract ContentDAO getContentDAO();
}



