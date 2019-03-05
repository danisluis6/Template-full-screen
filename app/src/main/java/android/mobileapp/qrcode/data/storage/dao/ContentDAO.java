package android.mobileapp.qrcode.data.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.mobileapp.qrcode.data.storage.entities.Content;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */
@Dao
public interface ContentDAO {

    @Insert
    long saveItem(Content device);
}
