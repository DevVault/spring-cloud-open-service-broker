/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.servicebroker.autoconfigure.web.reactive;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.cloud.servicebroker.autoconfigure.web.BasicServiceInstanceService;
import org.springframework.cloud.servicebroker.autoconfigure.web.servlet.ApiVersionWebMvcAutoConfiguration;
import org.springframework.cloud.servicebroker.model.BrokerApiVersion;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.cloud.servicebroker.model.BrokerApiVersion.API_VERSION_ANY;
import static org.springframework.cloud.servicebroker.model.BrokerApiVersion.API_VERSION_CURRENT;

class ApiVersionWebFluxAutoConfigurationTests {

	@Test
	void apiVersionBeansAreNotCreatedWithNonWebConfiguration() {
		nonWebApplicationContextRunner().withUserConfiguration(ServicesConfiguration.class).run((context) -> {
			assertThat(context).doesNotHaveBean(BrokerApiVersion.class);
			assertThat(context).doesNotHaveBean(ApiVersionWebFilter.class);
		});
	}

	@Test
	void apiVersionBeansAreNotCreatedWithoutServiceBeans() {
		webApplicationContextRunner().run((context) -> {
			assertThat(context).doesNotHaveBean(BrokerApiVersion.class);
			assertThat(context).doesNotHaveBean(ApiVersionWebFilter.class);
		});
	}

	@Test
	void apiVersionCheckIsDisabled() {
		webApplicationContextRunner().withUserConfiguration(ServicesConfiguration.class)
			.withPropertyValues("spring.cloud.openservicebroker.api-version-check-enabled=false")
			.run((context) -> {
				assertThat(context).doesNotHaveBean(BrokerApiVersion.class);
				assertThat(context).doesNotHaveBean(ApiVersionWebFilter.class);
			});
	}

	@Test
	void apiVersionBeansAreCreatedWithDefault() {
		webApplicationContextRunner().withUserConfiguration(ServicesConfiguration.class).run((context) -> {
			assertThat(context).getBean(BrokerApiVersion.class)
				.hasFieldOrPropertyWithValue("apiVersion", API_VERSION_ANY);
			assertThat(context).hasSingleBean(ApiVersionWebFilter.class);
		});
	}

	@Test
	void apiVersionBeansAreCreatedWithCustomVersion() {
		webApplicationContextRunner()
			.withUserConfiguration(ServicesConfiguration.class, CustomBrokerApiVersionConfigurationFromBean.class)
			.run((context) -> {
				assertThat(context).getBean(BrokerApiVersion.class)
					.hasFieldOrPropertyWithValue("apiVersion", API_VERSION_CURRENT);
				assertThat(context).hasSingleBean(ApiVersionWebFilter.class);
			});
	}

	@Test
	void apiVersionBeansAreCreatedFromCustomVersionProperty() {
		webApplicationContextRunner()
			.withUserConfiguration(ServicesConfiguration.class, CustomBrokerApiVersionConfigurationFromProperty.class)
			.run((context) -> {
				assertThat(context).getBean(BrokerApiVersion.class).hasFieldOrPropertyWithValue("apiVersion", "42.321");
				assertThat(context).hasSingleBean(ApiVersionWebFilter.class);
			});
	}

	@Test
	void apiVersionBeansAreCreatedFromCustomVersionBeanOverridesProperty() {
		webApplicationContextRunner()
			.withUserConfiguration(ServicesConfiguration.class,
					CustomBrokerApiVersionConfigurationFromBeanAndProperty.class)
			.run((context) -> {
				assertThat(context).getBean(BrokerApiVersion.class).hasFieldOrPropertyWithValue("apiVersion", "99.999");
				assertThat(context).hasSingleBean(ApiVersionWebFilter.class);
			});
	}

	private ReactiveWebApplicationContextRunner webApplicationContextRunner() {
		return new ReactiveWebApplicationContextRunner().withConfiguration(AutoConfigurations
			.of(ApiVersionWebFluxAutoConfiguration.class, ApiVersionWebMvcAutoConfiguration.class));
	}

	private ApplicationContextRunner nonWebApplicationContextRunner() {
		return new ApplicationContextRunner().withConfiguration(AutoConfigurations
			.of(ApiVersionWebFluxAutoConfiguration.class, ApiVersionWebMvcAutoConfiguration.class));
	}

	@Configuration
	static class ServicesConfiguration {

		@Bean
		ServiceInstanceService serviceInstanceService() {
			return new BasicServiceInstanceService();
		}

	}

	@Configuration
	static class CustomBrokerApiVersionConfigurationFromBean {

		@Bean
		BrokerApiVersion version() {
			return new BrokerApiVersion(API_VERSION_CURRENT);
		}

	}

	@Configuration
	@PropertySource("classpath:apiversion.properties")
	static class CustomBrokerApiVersionConfigurationFromProperty {

	}

	@Configuration
	@PropertySource("classpath:apiversion.properties")
	static class CustomBrokerApiVersionConfigurationFromBeanAndProperty {

		@Bean
		BrokerApiVersion version() {
			return new BrokerApiVersion("99.999");
		}

	}

}
