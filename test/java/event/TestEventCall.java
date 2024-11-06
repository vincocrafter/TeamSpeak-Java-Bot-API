package event;

import net.devcube.vinco.teamspeakapi.api.api.event.EventHandler;
import net.devcube.vinco.teamspeakapi.api.api.event.EventManager;
import net.devcube.vinco.teamspeakapi.api.api.event.TsEventAdapter;
import net.devcube.vinco.teamspeakapi.api.api.events.*;
import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.property.*;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedQueryLogin;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.VirtualServerInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestEventCall {

    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;
    private static Ts3SyncAPI sync;
    private static EventManager ev;
    private static QueryClientInfo queryClientInfo;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011, "serveradmin", password, 1, "EventCallTest", -1);
            }
        });
        basic = query.getBasicAPI();
        sync = query.getSyncAPI();
        ev = query.getEventManager();
        queryClientInfo = basic.getQueryInfo();
    }


    @AfterAll
    public static void quitQuery() {
        query.stopQuery();
    }

    @Test
    public void testChannelCreateAndDelete() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onChannelCreate(ChannelCreateEvent e) {
                e.getClientID();
                assertNotNull(e.getClientName());
                assertNotNull(e.getClientUUID());
                e.getChannelID();
                e.getChannelParentID();
                assertNotNull(e.getChannelName());
                assertNotNull(e.getChannelTopic());
                assertSame(ChannelCodec.OPUS_VOICE, e.getChannelCodec());
                assertEquals(10, e.getChannelQuality());
                e.getChannelOrder();
                e.getChannelMaxClients();
                e.getChannelMaxFamilyClients();
                assertFalse(e.isChannelPermanent());
                assertFalse(e.isChannelSemiPermanent());
                assertFalse(e.isChannelDefault());
                assertTrue(e.hasChannelPassword());
                assertFalse(e.isUnencrypted());
                e.getDeleteDelay();
                assertFalse(e.isMaxClientsUnlimited());
                assertFalse(e.isMaxFamilyClientsUnlimited());
                assertFalse(e.isMaxFamilyClientsInherited());
                assertEquals(75, e.getNeededTalkPower());
                assertNotNull(e.getNamePhonetic());
                assertNotNull(e.toString());
            }

            @EventHandler
            public void onChannelDeleted(ChannelDeletedEvent e) {
                assertNotNull(e.getClientName());
                e.getClientID();
                assertNotNull(e.getClientUUID());
                e.getChannelID();
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);
        Map<ChannelProperty, String> properties = new HashMap<>();
        properties.put(ChannelProperty.CHANNEL_TOPIC, "TestTopic");
        properties.put(ChannelProperty.CHANNEL_DESCRIPTION, "TestDescription");
        properties.put(ChannelProperty.CHANNEL_CODEC, ChannelCodec.OPUS_VOICE.getValue() + "");
        properties.put(ChannelProperty.CHANNEL_CODEC_QUALITY, "10");
        properties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "10");
        properties.put(ChannelProperty.CHANNEL_MAXFAMILYCLIENTS, "10");
        properties.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_SEMI_PERMANENT, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_DEFAULT, "0");
        properties.put(ChannelProperty.CHANNEL_PASSWORD, "Password");
        properties.put(ChannelProperty.CHANNEL_CODEC_IS_UNENCRYPTED, "0");
        properties.put(ChannelProperty.CHANNEL_DELETE_DELAY, "50");
        properties.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_MAXFAMILYCLIENTS_UNLIMITED, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_MAXFAMILYCLIENTS_INHERITED, "0");
        properties.put(ChannelProperty.CHANNEL_NEEDED_TALK_POWER, "75");
        properties.put(ChannelProperty.CHANNEL_NAME_PHONETIC, "TestChannelPhonetic");

        int createdChannel = basic.createChannel("TestChannel", properties);
        basic.deleteChannel(createdChannel, true);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {

        }
        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testChannelEdit() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onChannelEdit(ChannelEditedEvent e) {
                e.getClientID();
                assertNotNull(e.getClientName());
                assertNotNull(e.getClientUUID());
                e.getChannelID();
                e.getReasonID();
                e.hasChannelNameBeenEdited();
                assertNotNull(e.getEditedChannelName());
                e.hasChannelTopicBeenEdited();
                assertNotNull(e.getEditedChannelTopic());
                e.hasChannelCodecBeenEdited();
                assertSame(ChannelCodec.OPUS_MUSIC.getValue(), e.getEditedChannelCodecInt());
                assertSame(ChannelCodec.OPUS_MUSIC, e.getEditedChannelCodec());
                e.hasChannelCodecQualityBeenEdited();
                assertEquals(7, e.getEditedChannelCodecQuality());
                e.hasChannelMaxClientsBeenEdited();
                assertEquals(15, e.getEditedChannelMaxClients());
                e.hasChannelMaxFamilyClientsBeenEdited();
                assertEquals(35, e.getEditedChannelMaxFamilyClients());
                e.hasChannelOrderBeenEdited();
                assertEquals(55, e.getEditedChannelOrder());
                e.hasChannelPermanentBeenEdited();
                assertFalse(e.isEditedChannelPermanent());
                e.hasChannelSemiPermanentBeenEdited();
                assertTrue(e.isEditedChannelSemiPermanent());
                e.hasChannelDefaultBeenEdited();
                assertFalse(e.isEditedChannelDefault());
                e.hasChannelPasswordBeenEdited();
                assertTrue(e.hasEditedChannelPassword());
                e.hasChannelCodecLatencyFactorBeenEdited();
                assertEquals(-2.0, e.getEditedChannelCodecLatencyFactor());
                e.hasChannelCodecUnencryptedBeenEdited();
                assertFalse(e.isEditedChannelCodecUnencrypted());
                e.hasChannelDeleteDelayBeenEdited();
                assertEquals(-2, e.getEditedChannelDeleteDelay());
                e.hasChannelMaxClientsUnlimitedBeenEdited();
                assertFalse(e.isEditedChannelMaxClientsUnlimited());
                e.hasChannelMaxFamilyClientsUnlimitedBeenEdited();
                assertFalse(e.isEditedChannelMaxFamilyClientsUnlimited());
                e.hasChannelMaxFamilyClientsInheritedBeenEdited();
                assertTrue(e.isEditedChannelMaxFamilyClientsInherited());
                e.hasChannelNeededTalkPowerBeenEdited();
                assertEquals(42, e.getEditedChannelNeededTalkPower());
                e.hasChannelNamePhoneticBeenEdited();
                assertNotNull(e.getEditedChannelNamePhonetic());
                assertEquals("TestChannelPhonetic", e.getEditedChannelNamePhonetic());
                e.hasChannelIconIDBeenEdited();
                assertEquals(-2, e.getEditedChannelIconID());
                assertNotNull(e.toString());
            }
        };
        ChannelInfo info = basic.getChannel(54);
        ev.addTs3Listener(adapter);
        Map<ChannelProperty, String> properties = new HashMap<>();
        properties.put(ChannelProperty.CHANNEL_NAME, "EditedChannel1");
        properties.put(ChannelProperty.CHANNEL_TOPIC, "EditedChannel1Topic");
        properties.put(ChannelProperty.CHANNEL_CODEC, ChannelCodec.OPUS_MUSIC.getValue() + "");
        properties.put(ChannelProperty.CHANNEL_CODEC_QUALITY, "7");
        properties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "15");
        properties.put(ChannelProperty.CHANNEL_MAXFAMILYCLIENTS, "35");
        properties.put(ChannelProperty.CHANNEL_ORDER, "55");
        properties.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_SEMI_PERMANENT, "1");
        properties.put(ChannelProperty.CHANNEL_FLAG_DEFAULT, "0");
        properties.put(ChannelProperty.CHANNEL_PASSWORD, "Password");
        properties.put(ChannelProperty.CHANNEL_CODEC_IS_UNENCRYPTED, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_MAXFAMILYCLIENTS_UNLIMITED, "0");
        properties.put(ChannelProperty.CHANNEL_FLAG_MAXFAMILYCLIENTS_INHERITED, "1");
        properties.put(ChannelProperty.CHANNEL_NEEDED_TALK_POWER, "42");
        properties.put(ChannelProperty.CHANNEL_NAME_PHONETIC, "TestChannelPhonetic");

        basic.editChannel(54, properties);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);

        Map<ChannelProperty, String> reset = new HashMap<>();
        for (ChannelProperty key : properties.keySet()) {
            reset.put(key, info.getSplitMap().get(key.getValue()));
        }
        basic.editChannel(54, reset);
    }

    @Test
    public void testChannelDescriptionEdit() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
                e.getChannelID();
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);
        String curDesc = basic.getChannel(54).getDescription();

        basic.editChannel(54, Collections.singletonMap(ChannelProperty.CHANNEL_DESCRIPTION, "NewDescription"));
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);
        basic.editChannel(54, Collections.singletonMap(ChannelProperty.CHANNEL_DESCRIPTION, curDesc));
    }


    @Test
    public void testChannelMove() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onChannelMoved(ChannelMovedEvent e) {
                assertEquals(58, e.getChannelID());
                e.getChannelParentID();
                e.getOrder();
                e.getReasonID();
                e.getInvokerID();
                assertNotNull(e.getInvokerName());
                assertNotNull(e.getInvokerUUID());
                assertEquals(-2, e.getClientID());
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);

        basic.moveChannel(58, 57, 0);
        basic.moveChannel(58, 0, 57);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testChannelPasswordChanged() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
                e.getChannelID();
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);
        basic.editChannel(56, Collections.singletonMap(ChannelProperty.CHANNEL_PASSWORD, "NewPassword"));
        basic.editChannel(56, Collections.singletonMap(ChannelProperty.CHANNEL_PASSWORD, ""));
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testClientJoin() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onClientJoin(ClientJoinEvent e) {
                e.getClientID();
                assertNotNull(e.getClientName());
                assertNotNull(e.getClientUUID());
                assertEquals(1, e.getTargetChannelID());
                assertEquals(0, e.getSourceChannelID());
                assertEquals(0, e.getReasonID());
                e.isClientInputMuted();
                e.isClientOutputMuted();
                e.isClientOutputOnlyMuted();
                e.isClientInputHardware();
                e.isClientOutputHardware();
                e.isClientRecording();
                e.getClientDataBaseID();
                e.getClientChannelGroupID();
                e.getClientChannelGroupInheritedChannelID();
                assertEquals(15, e.getServerGroups().size());
                e.isClientAway();
                assertNotNull(e.getAwayMessage());
                assertSame(ClientType.QUERY, e.getClientType());
                assertTrue(e.isServerQueryClient());
                assertFalse(e.isUser());
                e.getClientTalkPower();
                e.isClientTalkPowerRequesting();
                assertNotNull(e.getClientTalkPowerRequestMsg());
                assertNotNull(e.getClientDescription());
                e.isClientTalker();
                e.isClientPrioritySpeaker();
                assertNotNull(e.getClientPhoneticName());
                e.getClientNeededServerQueryViewPower();
                e.getClientIconID();
                e.isClientChannelCommander();
                e.getCountry();
                assertNotNull(e.getClientMyTeamspeakID());
                assertNotNull(e.getClientBadges());
                assertNotNull(e.getClientIntegrations());
                assertNotNull(e.getClientMyTeamSpeakAvatar());
                assertNotNull(e.getClientAvatar());
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);

        Ts3ServerQuery otherQuery = new Ts3ServerQuery();
        try {
            otherQuery.connect("localhost", 10011);
            otherQuery.getSyncAPI().selectVirtualServer(1);
        } catch (IOException e) {
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
        otherQuery.stopQuery();
        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testClientLeave() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onClientLeave(ClientLeaveEvent e) {
                e.getClientID();
                e.getClientChannel();
                assertEquals(8, e.getReasonID());
                assertFalse(e.hasBeenKicked());
                assertFalse(e.hasBeenBanned());
                assertFalse(e.hasClientLostConnection());
                assertEquals(-2, e.getInvokerID());
                assertNotNull(e.getInvokerName());
                assertNotNull(e.getInvokerUUID());
                assertNotNull(e.getReasonMsg());
                assertEquals("disconnecting", e.getReasonMsg());
                assertEquals(-2, e.getBanTime());
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);

        Ts3ServerQuery otherQuery = new Ts3ServerQuery();
        try {
            otherQuery.connect("localhost", 10011);
            otherQuery.getSyncAPI().selectVirtualServer(1);
            otherQuery.stopQuery();
        } catch (IOException e) {
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testClientMove() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onClientMove(ClientMoveEvent e) {
                assertEquals(2, e.getClientIDs().size());
                e.getClientID();
                e.getReasonID();
                e.getTargetChannelID();
                assertTrue(e.isTargetChannelID(e.getTargetChannelID()));
                assertTrue(e.hasBeenMoved());
                e.getInvokerID();
                assertNotNull(e.getInvokerName());
                assertNotNull(e.getInvokerUUID());
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);

        Ts3ServerQuery otherQuery = new Ts3ServerQuery();
        try {
            otherQuery.connect("localhost", 10011);
            otherQuery.getSyncAPI().selectVirtualServer(1);
            List<Integer> ids = List.of(queryClientInfo.getClientID(), otherQuery.getBasicAPI().getQueryInfo().getClientID());
            basic.moveClientIDs(ids, 60);
            otherQuery.stopQuery();
        } catch (IOException e) {
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testPrivilegeKeyUsed() {
        int targetClientDBID = 26;
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
                e.getClientID();
                assertEquals(targetClientDBID, e.getClientDataBaseID());
                assertNotNull(e.getClientUUID());
                assertNotNull(e.getToken());
                assertEquals(24, e.getGroupID());
                assertEquals(0, e.getChannelID());
                assertSame(PrivilegeKeyType.SERVER_GROUP, e.getKeyType());
                assertNotNull(e.toString());
            }
        };
        ev.addTs3Listener(adapter);

        String key = basic.createPrivilegeKey(PrivilegeKeyType.SERVER_GROUP, 24, 0, null, null);
        CreatedQueryLogin login = basic.createQueryLogin("OtherClient", targetClientDBID);
        Ts3ServerQuery otherQuery = new Ts3ServerQuery();
        try {
            otherQuery.connect("localhost", 10011);
            otherQuery.getSyncAPI().login("OtherClient", login.getClientLoginPassword());
            otherQuery.getSyncAPI().selectVirtualServer(1);
            otherQuery.getBasicAPI().usePrivilegeKey(key);
            otherQuery.stopQuery();
        } catch (IOException | QueryLoginException e) {
        }
        basic.deleteQueryLogin(targetClientDBID);
        assertEquals(0, basic.getQueryLogins("OtherClient", -1, -1).size());
        assertEquals(0, basic.getPrivilegeKeys().size());

        ev.removeTs3Listener(adapter);
    }

    @Test
    public void testServerEdit() {

        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onServerEdit(ServerEditedEvent e) {
                e.getClientID();
                assertNotNull(e.getClientName());
                assertNotNull(e.getClientUUID());
                e.hasNameBeenEdited();
                assertNotNull(e.getEditedName());
                e.hasCodecEncryptionModeBeenEdited();
                e.getEditedEncryptionModeInt();
                assertSame(EncryptionMode.GLOBAL_ENABLED, e.getEditedEncryptionMode());
                e.hasDefaultServerGroupBeenEdited();
                assertEquals(9, e.getEditedDefaultServerGroup());
                e.hasDefaultChannelGroupBeenEdited();
                assertEquals(24, e.getEditedDefaultChannelGroup());
                e.hasHostBannerURLBeenEdited();
                e.getEditedHostBannerURL();
                assertEquals("https://www.google.com", e.getEditedHostBannerURL());
                e.hasHostBannerGFXURLBeenEdited();
                assertEquals("https://www.google.com", e.getEditedHostBannerGFXURL());
                e.hasHostBannerGFXIntervalBeenEdited();
                assertEquals(1000, e.getEditedHostBannerGFXInterval());
                e.hasPrioritySpeakerModifierBeenEdited();
                assertEquals(-19, e.getEditedPrioritySpeakerModifier());
                e.hasTooltipBeenEdited();
                assertEquals("TestTooltip", e.getEditedTooltip());
                e.hasHostbuttonURLBeenEdited();
                assertEquals("https://wikipedia.org", e.getEditedHostbuttonURL());
                e.hasHostbuttonGFXURLBeenEdited();
                assertEquals("https://wikipedia.org", e.getEditedHostbuttonGFXURL());
                e.hasPhoneticNameBeenEdited();
                assertEquals("TestServerPhonetic", e.getEditedPhoneticName());
                e.hasIconIDBeenEdited();
                assertEquals(-2, e.getEditedIconID());
                e.hasHostBannerModeBeenEdited();
                e.getEditedHostBannerModeInt();
                assertSame(HostBannerMode.KEEPASPECT, e.getEditedHostBannerMode());
                e.hasChannelDeleteDelayBeenEdited();
                e.getEditedChannelDeleteDelay();
                assertEquals(5, e.getEditedChannelDeleteDelay());
                e.getReasonID();
                assertNotNull(e.toString());
            }
        };
        ev.addTs3Listener(adapter);
        VirtualServerInfo oldInfo = basic.getVirtualServerInfo();

        Map<VirtualServerProperty, String> properties = new HashMap<>();
        properties.put(VirtualServerProperty.VIRTUALSERVER_NAME, "TestServer");
        properties.put(VirtualServerProperty.VIRTUALSERVER_CODEC_ENCRYPTION_MODE, EncryptionMode.GLOBAL_ENABLED.getValue() + "");
        properties.put(VirtualServerProperty.VIRTUALSERVER_DEFAULT_SERVER_GROUP, "9");
        properties.put(VirtualServerProperty.VIRTUALSERVER_DEFAULT_CHANNEL_GROUP, "24");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_URL, "https://www.google.com");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_GFX_URL, "https://www.google.com");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_GFX_INTERVAL, "1000");
        properties.put(VirtualServerProperty.VIRTUALSERVER_PRIORITY_SPEAKER_DIMM_MODIFICATOR, "-19");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBUTTON_TOOLTIP, "TestTooltip");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBUTTON_URL, "https://wikipedia.org");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBUTTON_GFX_URL, "https://wikipedia.org");
        properties.put(VirtualServerProperty.VIRTUALSERVER_NAME_PHONETIC, "TestServerPhonetic");
        properties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_MODE, HostBannerMode.KEEPASPECT.getValue() + "");
        properties.put(VirtualServerProperty.VIRTUALSERVER_CHANNEL_TEMP_DELETE_DELAY_DEFAULT, "5");

        basic.editVirtualServer(properties);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);

        Map<VirtualServerProperty, String> resetProperties = new HashMap<>();
        for (VirtualServerProperty key : properties.keySet()) {
            String value = oldInfo.getSplitMap().get(key.getValue());
            if (value == null)
                value = "";
            resetProperties.put(key, value);
        }
        basic.editVirtualServer(resetProperties);
        sync.editChannel(54, ChannelProperty.CHANNEL_CODEC_IS_UNENCRYPTED, "1");
        sync.editChannel(1, ChannelProperty.CHANNEL_CODEC_IS_UNENCRYPTED, "1");
    }

    @Test
    public void testTextMessage() {
        TsEventAdapter adapter = new TsEventAdapter() {
            @EventHandler
            public void onTextMessage(TextMessageEvent e) {
                assertEquals(queryClientInfo.getClientID(), e.getClientID());
                assertNotNull(e.getClientName());
                assertEquals(TextMessageType.CLIENT.getValue(), e.getTargetModeID());
                assertEquals("TestMessage", e.getMessage());
                assertSame(TextMessageType.CLIENT, e.getTextMessageType());
                assertSame(TextMessageType.CLIENT, e.getTargetMode());
                assertEquals(queryClientInfo.getClientID(), e.getTargetID());
                assertNotNull(e.toString());
            }
        };

        ev.addTs3Listener(adapter);
        basic.sendTextMessage(TextMessageType.CLIENT, queryClientInfo.getClientID(), "TestMessage");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
        ev.removeTs3Listener(adapter);
    }
}
