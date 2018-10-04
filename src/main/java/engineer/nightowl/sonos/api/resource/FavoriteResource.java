package engineer.nightowl.sonos.api.resource;

import engineer.nightowl.sonos.api.SonosApiClient;
import engineer.nightowl.sonos.api.domain.SonosFavorite;
import engineer.nightowl.sonos.api.domain.SonosFavoriteList;
import engineer.nightowl.sonos.api.domain.SonosPlayMode;
import engineer.nightowl.sonos.api.domain.SonosSuccess;
import engineer.nightowl.sonos.api.exception.SonosApiClientException;
import engineer.nightowl.sonos.api.exception.SonosApiError;
import engineer.nightowl.sonos.api.specs.Subscribable;

import java.util.HashMap;
import java.util.Map;

/**
 * Favorites are 'shortcuts' to playlists, albums, artists etc.
 * <p>
 * Reference: <a href="https://developer.sonos.com/reference/control-api/favorites/">Favorites API</a>
 *
 * @see SonosFavorite
 */
public class FavoriteResource extends BaseResource implements Subscribable
{
    public FavoriteResource(final SonosApiClient apiClient)
    {
        super(apiClient);
    }

    /**
     * Get favorites for the specified household
     *
     * @param clientToken for the user
     * @param householdId for the household you want to fetch favorites
     * @return the favorites for the specified household
     * @throws SonosApiClientException if an error occurs during the call
     * @throws SonosApiError           if the API returns an error
     */
    public SonosFavoriteList getFavorites(final String clientToken, final String householdId) throws SonosApiClientException, SonosApiError
    {
        return getFromApi(SonosFavoriteList.class, clientToken, String.format("/v1/households/%s/favorites", householdId));
    }

    public SonosSuccess loadFavorite(final String clientToken, final String groupId, final String favoriteId, final Boolean playOnCompletion,
                                     final SonosPlayMode playMode) throws SonosApiClientException, SonosApiError
    {
        validateNotNull(favoriteId, "favoriteId");
        final Map<String, Object> payload = new HashMap<>();
        if (playOnCompletion != null)
        {
            payload.put("playOnCompletion", playOnCompletion);
        }
        if (playMode != null)
        {
            payload.put("playModes", playMode);
        }
        payload.put("favoriteId", favoriteId);

        return postToApi(SonosSuccess.class, clientToken, String.format("/v1/groups/%s/favorites", groupId), payload);
    }

    @Override
    public SonosSuccess subscribe(final String clientToken, final String householdId) throws SonosApiClientException, SonosApiError
    {
        return postToApi(SonosSuccess.class, clientToken, String.format("/v1/households/%s/favorites/subscription", householdId));
    }

    @Override
    public SonosSuccess unsubscribe(final String clientToken, final String householdId) throws SonosApiClientException, SonosApiError
    {
        return deleteFromApi(SonosSuccess.class, clientToken, String.format("/v1/households/%s/favorites/subscription", householdId));
    }
}
