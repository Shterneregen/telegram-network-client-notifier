package random.telegramhomebot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import random.telegramhomebot.model.FeatureSwitcher;
import random.telegramhomebot.repository.FeatureSwitcherRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeatureSwitcherService {

	public enum Features {NEW_HOSTS_NOTIFICATION, REACHABLE_HOSTS_NOTIFICATION, NOT_REACHABLE_HOSTS_NOTIFICATION}

	private final FeatureSwitcherRepository repository;

	public Optional<FeatureSwitcher> getFeatureSwitcherByName(String featureName) {
		return repository.findFeatureSwitcherByName(featureName);
	}

	public void saveFeatureSwitcher(FeatureSwitcher featureSwitcher) {
		repository.save(featureSwitcher);
	}

	public void switchFeature(String featureName) {
		Optional<FeatureSwitcher> featureSwitcherOptional = getFeatureSwitcherByName(featureName);
		if (featureSwitcherOptional.isPresent()) {
			FeatureSwitcher featureSwitcher = featureSwitcherOptional.get();
			featureSwitcher.setEnabled(!featureSwitcher.getEnabled());
			saveFeatureSwitcher(featureSwitcher);
		}
	}

	public boolean newHostsNotificationsEnabled() {
		return isFeatureEnabled(Features.NEW_HOSTS_NOTIFICATION);
	}

	public boolean reachableHostsNotificationsEnabled() {
		return isFeatureEnabled(Features.REACHABLE_HOSTS_NOTIFICATION);
	}

	public boolean notReachableHostsNotificationsEnabled() {
		return isFeatureEnabled(Features.NOT_REACHABLE_HOSTS_NOTIFICATION);
	}

	private boolean isFeatureEnabled(Features featureName) {
		Optional<FeatureSwitcher> feature = getFeatureSwitcherByName(featureName.name());
		return feature.isPresent() ? feature.get().getEnabled() : false;
	}
}