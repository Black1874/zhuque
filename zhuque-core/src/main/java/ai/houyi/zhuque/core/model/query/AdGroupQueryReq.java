/*
 * Copyright 2017-2019 The OpenAds Project
 *
 * The OpenAds Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package ai.houyi.zhuque.core.model.query;

import org.apache.commons.lang3.StringUtils;

import ai.houyi.zhuque.commons.SQLUtils;
import ai.houyi.zhuque.commons.model.PageQueryReq;
import ai.houyi.zhuque.dao.model.AdGroupExample;

/**
 *
 * @author weiping wang
 */
public class AdGroupQueryReq extends PageQueryReq<AdGroupExample> {
	private Integer advertiserId;
	private Integer campaignId;
	private Integer id;

	private String camppaignName;
	private String name;

	@Override
	public AdGroupExample toExample() {
		AdGroupExample example = new AdGroupExample();
		example.limit(getOffset(), pageSize);

		AdGroupExample.Criteria criteria = example.createCriteria();
		if (campaignId != null)
			criteria.andCampaignIdEqualTo(campaignId);
		if (id != null)
			criteria.andIdEqualTo(id);
		if (StringUtils.isNotBlank(name))
			criteria.andNameLike(SQLUtils.toLikeString(name));
		
		return example;
	}

	/**
	 * @return the advertiserId
	 */
	public Integer getAdvertiserId() {
		return advertiserId;
	}

	/**
	 * @param advertiserId the advertiserId to set
	 */
	public void setAdvertiserId(Integer advertiserId) {
		this.advertiserId = advertiserId;
	}

	/**
	 * @return the campaignId
	 */
	public Integer getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	

	/**
	 * @return the camppaignName
	 */
	public String getCamppaignName() {
		return camppaignName;
	}

	/**
	 * @param camppaignName the camppaignName to set
	 */
	public void setCamppaignName(String camppaignName) {
		this.camppaignName = camppaignName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}