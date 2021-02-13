package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class VerifyResponse {

    private final boolean success;
    private final List<CloudFlareError> errors;
    private final List<CloudFlareMessage> messages;
    private final VerifyResult result;

    public boolean isSuccess() {
        return success;
    }

    public List<CloudFlareError> getErrors() {
        return errors;
    }

    public List<CloudFlareMessage> getMessages() {
        return messages;
    }

    public VerifyResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "VerifyResponse{" +
               "success=" + success +
               ", errors=" + errors +
               ", messages=" + messages +
               ", result=" + result +
               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VerifyResponse)) {
            return false;
        }

        final VerifyResponse that = (VerifyResponse) o;

        if (success != that.success) {
            return false;
        }
        if (!Objects.equals(errors, that.errors)) {
            return false;
        }
        if (!Objects.equals(messages, that.messages)) {
            return false;
        }
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        int result1 = (success ? 1 : 0);
        result1 = 31 * result1 + (errors != null ? errors.hashCode() : 0);
        result1 = 31 * result1 + (messages != null ? messages.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    public VerifyResponse(final boolean success,
                          final List<CloudFlareError> errors,
                          final List<CloudFlareMessage> messages,
                          final VerifyResult result) {
        this.success = success;
        this.errors = errors;
        this.messages = messages;
        this.result = result;
    }

    private static class VerifyResult {
        private final String id;
        private final String status;
        @SerializedName("not_before")
        private final String notBefore;

        @SerializedName("expires_on")
        private final String expiresOn;

        private VerifyResult(final String id, final String status, final String notBefore, final String expiresOn) {
            this.id = id;
            this.status = status;
            this.notBefore = notBefore;
            this.expiresOn = expiresOn;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (status != null ? status.hashCode() : 0);
            result = 31 * result + (notBefore != null ? notBefore.hashCode() : 0);
            result = 31 * result + (expiresOn != null ? expiresOn.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof VerifyResult)) {
                return false;
            }

            final VerifyResult that = (VerifyResult) o;

            if (!Objects.equals(id, that.id)) {
                return false;
            }
            if (!Objects.equals(status, that.status)) {
                return false;
            }
            if (!Objects.equals(notBefore, that.notBefore)) {
                return false;
            }
            return Objects.equals(expiresOn, that.expiresOn);
        }

        @Override
        public String toString() {
            return "VerifyResult{" +
                   "id='" + id + '\'' +
                   ", status='" + status + '\'' +
                   ", notBefore=" + notBefore +
                   ", expiresOn=" + expiresOn +
                   '}';
        }

        public String getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public String getNotBefore() {
            return notBefore;
        }

        public String getExpiresOn() {
            return expiresOn;
        }
    }

}
