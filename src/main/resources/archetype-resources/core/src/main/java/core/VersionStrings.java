#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.utils.JsonValue;

public class VersionStrings {

	public String fullVersion;
	public String shortVersion;
	public String versionId;

	public VersionStrings(final JsonValue version) {
		final String versionNumber = getValue(version, "version");
		final String buildNumber = getValue(version, "buildNumber");
		final String buildDate = getValue(version, "buildDate");
		final String commitId = getValue(version, "commit");

		final StringBuilder sbFullVersion = new StringBuilder();
		final StringBuilder sbShortVersion = new StringBuilder();
		final StringBuilder sbVersionId = new StringBuilder();

		if (!versionNumber.isEmpty()) {
			sbFullVersion.append(" ").append(versionNumber);
			sbShortVersion.append(" ").append(versionNumber);
		}

		if (!buildNumber.isEmpty()) {
			sbFullVersion.append(" ").append("Build ").append(buildNumber);
			if (versionNumber.isEmpty() || isSnapshotVersion(versionNumber)) {
				sbShortVersion.append(" ").append("Build ").append(buildNumber);
			}
			sbVersionId.append(" ").append(buildNumber);
		} else if (!commitId.isEmpty()) {
			sbFullVersion.append(" ").append("Commit ").append(commitId);
			if (versionNumber.isEmpty() || isSnapshotVersion(versionNumber)) {
				sbShortVersion.append(" ").append("Commit ").append(commitId);
			}
			sbVersionId.append(" ").append(commitId);
		}

		if (!buildDate.isEmpty()) {
			if (sbFullVersion.length() > 0) {
				sbFullVersion.append(" ").append("(").append(buildDate).append(")");
			} else {
				sbFullVersion.append(" ").append(buildDate);
			}
			if (isSnapshotVersion(versionNumber) && buildNumber.isEmpty() && commitId.isEmpty()) {
				sbShortVersion.append(" ").append("(").append(buildDate).append(")");
			}
			if (versionNumber.isEmpty() && sbShortVersion.length() == 0) {
				sbShortVersion.append(" ").append(buildDate);
			}
			if (sbVersionId.length() == 0) {
				sbVersionId.append(" ").append(buildDate);
			}
		}

		if (sbVersionId.length() == 0) {
			sbVersionId.append(" ").append(versionNumber);
		}

		if (sbFullVersion.length() > 0) {
			fullVersion = sbFullVersion.toString().substring(1);
		} else {
			fullVersion = "";
		}
		if (sbShortVersion.length() > 0) {
			shortVersion = sbShortVersion.toString().substring(1);
		} else {
			shortVersion = "";
		}
		if (sbVersionId.length() > 0) {
			versionId = sbVersionId.toString().substring(1);
		} else {
			versionId = "";
		}
	}

	private static String getValue(final JsonValue jsonValue, final String key) {
		final String rawValue = jsonValue.getString(key, "");
		if (rawValue != null && !rawValue.startsWith("${symbol_dollar}{")) {
			return rawValue;
		} else {
			return "";
		}
	}

	private static boolean isSnapshotVersion(final String version) {
		return version != null && version.contains("SNAPSHOT");
	}

}
