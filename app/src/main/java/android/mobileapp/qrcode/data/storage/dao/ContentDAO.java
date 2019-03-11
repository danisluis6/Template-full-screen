package android.mobileapp.qrcode.data.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.mobileapp.qrcode.data.DatabaseInfo;
import android.mobileapp.qrcode.data.storage.entities.Content;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */
@Dao
public interface ContentDAO {

    @Insert
    long saveItem(Content content);

    @Query("SELECT * FROM "+ DatabaseInfo.Tables.Content)
    Maybe<List<Content>> getAll();

    @Delete
    int deleteItems(Content... contents);
}
