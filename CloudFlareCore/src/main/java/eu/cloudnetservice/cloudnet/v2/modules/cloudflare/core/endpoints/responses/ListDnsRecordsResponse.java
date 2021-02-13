/*
 * Copyright 2017 Tarek Hosni El Alaoui
 * Copyright 2021 CloudNetService
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses;

import com.google.gson.annotations.SerializedName;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;

import java.util.List;
import java.util.Objects;

public class ListDnsRecordsResponse extends CloudflareResponse {
    private final List<CloudflareDnsRecord> result;
    @SerializedName("result_info")
    private final ListResultInfo resultInfo;

    public ListDnsRecordsResponse(final boolean success,
                                  final List<CloudFlareError> errors,
                                  final List<CloudFlareMessage> messages,
                                  final List<CloudflareDnsRecord> result,
                                  final ListResultInfo resultInfo) {
        super(success, errors, messages);
        this.result = result;
        this.resultInfo = resultInfo;
    }

    public ListResultInfo getResultInfo() {
        return resultInfo;
    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (resultInfo != null ? resultInfo.hashCode() : 0);
        return result1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListDnsRecordsResponse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final ListDnsRecordsResponse that = (ListDnsRecordsResponse) o;

        if (!Objects.equals(result, that.result)) {
            return false;
        }
        return Objects.equals(resultInfo, that.resultInfo);
    }

    @Override
    public String toString() {
        return "ListDnsRecordsResponse{" +
               "result=" + result +
               ", resultInfo=" + resultInfo +
               "} " + super.toString();
    }

    public List<CloudflareDnsRecord> getResult() {
        return result;
    }

    public static class ListResultInfo {
        private final int page;

        @SerializedName("per_page")
        private final int perPage;
        private final int count;

        @SerializedName("total_count")
        private final int totalCount;

        @SerializedName("total_pages")
        private final int totalPages;

        private ListResultInfo(final int page, final int perPage, final int count, final int totalCount, final int totalPages) {
            this.page = page;
            this.perPage = perPage;
            this.count = count;
            this.totalCount = totalCount;
            this.totalPages = totalPages;
        }

        public int getPage() {
            return page;
        }

        public int getPerPage() {
            return perPage;
        }

        public int getCount() {
            return count;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public int getTotalPages() {
            return totalPages;
        }

        @Override
        public int hashCode() {
            int result = page;
            result = 31 * result + perPage;
            result = 31 * result + count;
            result = 31 * result + totalCount;
            result = 31 * result + totalPages;
            return result;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ListResultInfo)) {
                return false;
            }

            final ListResultInfo that = (ListResultInfo) o;

            if (page != that.page) {
                return false;
            }
            if (perPage != that.perPage) {
                return false;
            }
            if (count != that.count) {
                return false;
            }
            if (totalCount != that.totalCount) {
                return false;
            }
            return totalPages == that.totalPages;
        }

        @Override
        public String toString() {
            return "ListResultInfo{" +
                   "page=" + page +
                   ", perPage=" + perPage +
                   ", count=" + count +
                   ", totalCount=" + totalCount +
                   ", totalPages=" + totalPages +
                   '}';
        }
    }
}
