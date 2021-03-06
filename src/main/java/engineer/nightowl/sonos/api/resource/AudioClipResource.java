package engineer.nightowl.sonos.api.resource;

import engineer.nightowl.sonos.api.SonosApiClient;
import engineer.nightowl.sonos.api.domain.SonosAudioClip;
import engineer.nightowl.sonos.api.domain.SonosSuccess;
import engineer.nightowl.sonos.api.exception.SonosApiClientException;
import engineer.nightowl.sonos.api.exception.SonosApiError;

/**
 * <p>AudioClipResource class.</p>
 *
 * @see <a href="https://developer.sonos.com/reference/control-api/audioclip/">Sonos docs</a>
 */
public class AudioClipResource extends SubscribableResource {


    @Override
    String getSubscriptionPath()
    {
        return "/v1/players/%s/audioClip/subscription";
    }

    /**
     * <p>Constructor for AudioClipResource.</p>
     *
     * @param apiClient a {@link SonosApiClient} object.
     */
    public AudioClipResource(final SonosApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Schedule a provided audioClip for playback
     *
     * @param clientToken for the user
     * @param playerId    to play the audioClip on
     * @param audioClip   to schedule
     * @return the audioClip decorated with additional information
     * @throws engineer.nightowl.sonos.api.exception.SonosApiClientException if an error occurs during the call
     * @throws engineer.nightowl.sonos.api.exception.SonosApiError           if there is an error from the API
     * @see <a href="https://developer.sonos.com/reference/control-api/audioclip/loadaudioclip/">Sonos docs</a>
     */
    public SonosAudioClip loadAudioClip(final String clientToken, final String playerId, final SonosAudioClip audioClip)
            throws SonosApiClientException, SonosApiError {
        return postToApi(SonosAudioClip.class, clientToken, String.format("/v1/players/%s/audioClip", playerId), audioClip);
    }

    /**
     * Cancel a specified audioClip via its ID.
     *
     * @param clientToken for the user
     * @param playerId    the audioClip is scheduled to play on
     * @param clipId      of the audioClip
     * @return whether the clip was cancelled
     * @throws engineer.nightowl.sonos.api.exception.SonosApiClientException if an error occurs during the call
     * @throws engineer.nightowl.sonos.api.exception.SonosApiError           if there is an error from the API
     * @see <a href="https://developer.sonos.com/reference/control-api/audioclip/cancelaudioclip/">Sonos docs</a>
     */
    public SonosSuccess cancelAudioClip(final String clientToken, final String playerId, final String clipId)
            throws SonosApiClientException, SonosApiError {
        return deleteFromApi(SonosSuccess.class, clientToken, String.format("/v1/players/%s/audioClip/%s", playerId, clipId));
    }

}
