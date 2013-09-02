#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.utils.JsonValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VersionStringsTest {

	@Mock
	public JsonValue version;

	@Before
	public void setupDefaultValue() {
		when(version.getString(anyString(), anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(final InvocationOnMock invocation) throws Throwable {
				return (String) invocation.getArguments()[1];
			}
		});
	}

	private void useReleaseVersion() {
		when(version.getString(eq("version"), anyString())).thenReturn("1.0.0.RELEASE");
	}

	private void useSnapshotVersion() {
		when(version.getString(eq("version"), anyString())).thenReturn("1.0.1.BUILD-SNAPSHOT");
	}

	private void useBuildNumber() {
		when(version.getString(eq("buildNumber"), anyString())).thenReturn("1501");
	}

	private void useCommitId() {
		when(version.getString(eq("commit"), anyString())).thenReturn("a1c2e3g");
	}

	private void useBuildDate() {
		when(version.getString(eq("buildDate"), anyString())).thenReturn("2013-09-01 20:22:21 PDT");
	}

	private void usePlaceholders() {
		when(version.getString(anyString(), anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(final InvocationOnMock invocation) throws Throwable {
				return "${symbol_dollar}{" + invocation.getArguments()[0] + "}";
			}
		});
	}

	private void check(final String fullVersion, final String shortVersion, final String versionId) {
		final VersionStrings v = new VersionStrings(version);
		assertThat("fullVersion", v.fullVersion, is(fullVersion));
		assertThat("shortVersion", v.shortVersion, is(shortVersion));
		assertThat("versionId", v.versionId, is(versionId));
	}

	@Test
	public void noValuesSet() {
		check("", "", "");
	}

	@Test
	public void noValuesSetWithPlaceholders() {
		usePlaceholders();
		check("", "", "");
	}

	@Test
	public void buildDateOnly() {
		useBuildDate();
		check("2013-09-01 20:22:21 PDT", "2013-09-01 20:22:21 PDT", "2013-09-01 20:22:21 PDT");
	}

	@Test
	public void buildNumberOnly() {
		useBuildNumber();
		check("Build 1501", "Build 1501", "1501");
	}

	@Test
	public void buildNumberAndBuildDate() {
		useBuildNumber();
		useBuildDate();
		check("Build 1501 (2013-09-01 20:22:21 PDT)", "Build 1501", "1501");
	}

	@Test
	public void buildNumberAndCommitId() {
		useBuildNumber();
		useCommitId();
		check("Build 1501", "Build 1501", "1501");
	}

	@Test
	public void buildNumberAndBuildDateAndCommitId() {
		useCommitId();
		useBuildNumber();
		useBuildDate();
		check("Build 1501 (2013-09-01 20:22:21 PDT)", "Build 1501", "1501");
	}

	@Test
	public void commitIdOnly() {
		useCommitId();
		check("Commit a1c2e3g", "Commit a1c2e3g", "a1c2e3g");
	}

	@Test
	public void commitIdAndBuildDate() {
		useCommitId();
		useBuildDate();
		check("Commit a1c2e3g (2013-09-01 20:22:21 PDT)", "Commit a1c2e3g", "a1c2e3g");
	}

	@Test
	public void snapshotVersionOnly() {
		useSnapshotVersion();
		check("1.0.1.BUILD-SNAPSHOT", "1.0.1.BUILD-SNAPSHOT", "1.0.1.BUILD-SNAPSHOT");
	}

	@Test
	public void snapshotVersionAndBuildDate() {
		useSnapshotVersion();
		useBuildDate();
		check("1.0.1.BUILD-SNAPSHOT (2013-09-01 20:22:21 PDT)", "1.0.1.BUILD-SNAPSHOT (2013-09-01 20:22:21 PDT)", "2013-09-01 20:22:21 PDT");
	}

	@Test
	public void snapshotVersionAndBuildNumber() {
		useSnapshotVersion();
		useBuildNumber();
		check("1.0.1.BUILD-SNAPSHOT Build 1501", "1.0.1.BUILD-SNAPSHOT Build 1501", "1501");
	}

	@Test
	public void snapshotVersionAndBuildNumberAndBuildDate() {
		useSnapshotVersion();
		useBuildNumber();
		useBuildDate();
		check("1.0.1.BUILD-SNAPSHOT Build 1501 (2013-09-01 20:22:21 PDT)", "1.0.1.BUILD-SNAPSHOT Build 1501", "1501");
	}

	@Test
	public void snapshotVersionAndCommitId() {
		useSnapshotVersion();
		useCommitId();
		check("1.0.1.BUILD-SNAPSHOT Commit a1c2e3g", "1.0.1.BUILD-SNAPSHOT Commit a1c2e3g", "a1c2e3g");
	}

	@Test
	public void snapshotVersionAndCommitIdAndBuildDate() {
		useSnapshotVersion();
		useCommitId();
		useBuildDate();
		check("1.0.1.BUILD-SNAPSHOT Commit a1c2e3g (2013-09-01 20:22:21 PDT)", "1.0.1.BUILD-SNAPSHOT Commit a1c2e3g", "a1c2e3g");
	}

	@Test
	public void releaseVersionOnly() {
		useReleaseVersion();
		check("1.0.0.RELEASE", "1.0.0.RELEASE", "1.0.0.RELEASE");
	}

	@Test
	public void releaseVersionAndBuildNumber() {
		useReleaseVersion();
		useBuildNumber();
		check("1.0.0.RELEASE Build 1501", "1.0.0.RELEASE", "1501");
	}

	@Test
	public void releaseVersionAndBuildNumberAndBuildDate() {
		useReleaseVersion();
		useBuildNumber();
		useBuildDate();
		check("1.0.0.RELEASE Build 1501 (2013-09-01 20:22:21 PDT)", "1.0.0.RELEASE", "1501");
	}

	@Test
	public void releaseVersionAndCommitId() {
		useReleaseVersion();
		useCommitId();
		check("1.0.0.RELEASE Commit a1c2e3g", "1.0.0.RELEASE", "a1c2e3g");
	}

	@Test
	public void releaseVersionAndBuildDate() {
		useReleaseVersion();
		useBuildDate();
		check("1.0.0.RELEASE (2013-09-01 20:22:21 PDT)", "1.0.0.RELEASE", "2013-09-01 20:22:21 PDT");
	}

	@Test
	public void releaseVersionAndBuildDateAndCommitId() {
		useReleaseVersion();
		useBuildDate();
		useCommitId();
		check("1.0.0.RELEASE Commit a1c2e3g (2013-09-01 20:22:21 PDT)", "1.0.0.RELEASE", "a1c2e3g");
	}

}
