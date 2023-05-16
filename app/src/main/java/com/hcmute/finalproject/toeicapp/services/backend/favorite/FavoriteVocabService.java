package com.hcmute.finalproject.toeicapp.services.backend.favorite;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.dao.FavoriteVocabGroupDao;
import com.hcmute.finalproject.toeicapp.dao.FavoriteVocabWordDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabGroup;
import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabWord;
import com.hcmute.finalproject.toeicapp.services.backend.authentication.AuthenticationService;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.BackupFavoriteGroup;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.BackupFavoriteRequest;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.BackupFavoriteResponse;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.BackupFavoriteVocab;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteGroup;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteRequest;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteResponse;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteVocab;
import com.hcmute.finalproject.toeicapp.services.base.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class FavoriteVocabService {
    private final Context context;
    private final FavoriteVocabWordDao favoriteVocabWordDao;
    private final FavoriteVocabGroupDao favoriteVocabGroupDao;
    private final AuthenticationService authenticationService;

    public FavoriteVocabService(@NonNull Context context) {
        this.context = context;

        this.authenticationService = new AuthenticationService(this.context);

        this.favoriteVocabGroupDao = ToeicAppDatabase
                .getInstance(this.context)
                .getFavoriteVocabGroupDao();

        this.favoriteVocabWordDao = ToeicAppDatabase
                .getInstance(this.context)
                .getFavoriteVocabWordDao();
    }

    private RestoreFavoriteRequest buildRestoreRequest() {
        final RestoreFavoriteRequest request = new RestoreFavoriteRequest();
        final List<FavoriteVocabGroup> groups = this.favoriteVocabGroupDao.getAll();
        final List<RestoreFavoriteGroup> restoreFavoriteGroups = new ArrayList<>();

        request.setGmail(this.authenticationService.getUserEmail());
        request.setGroups(restoreFavoriteGroups);

        for (FavoriteVocabGroup group : groups) {
            RestoreFavoriteGroup restoreFavoriteGroup = new RestoreFavoriteGroup();
            restoreFavoriteGroup.setGroupName(group.getGroupName());
            restoreFavoriteGroups.add(restoreFavoriteGroup);

            final List<RestoreFavoriteVocab> vocabs = this.favoriteVocabWordDao
                    .getByGroupId(group.getId())
                    .stream()
                    .map(item -> {
                        RestoreFavoriteVocab restoreFavoriteVocab = new RestoreFavoriteVocab();
                        restoreFavoriteVocab.setMeaning(item.getMeaning());
                        restoreFavoriteVocab.setWord(item.getWord());
                        return restoreFavoriteVocab;
                    })
                    .collect(Collectors.toList());

            restoreFavoriteGroup.setFavoriteVocabs(vocabs);
        }

        return request;
    }

    public void restoreFavoriteVocabsToServer() {
        final RestoreFavoriteRequest request = this.buildRestoreRequest();

        APIFavoriteVocab
                .getInstance()
                .restoreFavoriteDatabase(request)
                .enqueue(new Callback<RestoreFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<RestoreFavoriteResponse> call, Response<RestoreFavoriteResponse> response) {
                        Log.d("RESTORE_FAVORITE", "ok " + response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<RestoreFavoriteResponse> call, Throwable t) {
                        Log.d("RESTORE_FAVORITE", "fail " + Log.getStackTraceString(t));
                    }
                });
    }

    private void backupFavoriteVocabFromResponse(BackupFavoriteResponse response) {
        for (FavoriteVocabGroup group : this.favoriteVocabGroupDao.getAll()) {
            this.favoriteVocabGroupDao.delete(group);
        }

        final List<BackupFavoriteGroup> groups = response.getData().getGroups();
        for (BackupFavoriteGroup group : groups) {
            FavoriteVocabGroup groupEntity = new FavoriteVocabGroup();
            groupEntity.setGroupName(group.getGroupName());
            groupEntity.setId(Math.toIntExact(this.favoriteVocabGroupDao.insert(groupEntity).get(0)));

            final List<BackupFavoriteVocab> vocabs = group.getFavoriteVocabs();
            for (BackupFavoriteVocab vocab : vocabs) {
                FavoriteVocabWord vocabEntity = new FavoriteVocabWord();
                vocabEntity.setGroupId(groupEntity.getId());
                vocabEntity.setWord(vocab.getWord());
                vocabEntity.setMeaning(vocab.getMeaning());
                this.favoriteVocabWordDao.insert(vocabEntity);
            }
        }
    }

    public void backupFavoriteVocabToServer() {
        BackupFavoriteRequest request = new BackupFavoriteRequest();
        request.setGmail(this.authenticationService.getUserEmail());

        APIFavoriteVocab
                .getInstance()
                .backupFavoriteDatabase(request)
                .enqueue(new Callback<BackupFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<BackupFavoriteResponse> call, Response<BackupFavoriteResponse> response) {
                        backupFavoriteVocabFromResponse(response.body());
                        Log.d("BACKUP_FAVORITE", "ok " + response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<BackupFavoriteResponse> call, Throwable t) {
                        Log.d("BACKUP_FAVORITE", "fail " + Log.getStackTraceString(t));
                    }
                });
    }
}
