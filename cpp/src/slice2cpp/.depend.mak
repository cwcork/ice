
Gen.obj: \
	Gen.cpp \
    "$(includedir)\IceUtil\DisableWarnings.h" \
    "Gen.h" \
    "$(includedir)\Slice\Parser.h" \
    "$(includedir)\IceUtil\Shared.h" \
    "$(includedir)\IceUtil\Config.h" \
    "$(includedir)\IceUtil\Atomic.h" \
    "$(includedir)\IceUtil\Handle.h" \
    "$(includedir)\IceUtil\Exception.h" \
    "$(includedir)\IceUtil\OutputUtil.h" \
    "$(includedir)\Slice\Util.h" \
    "$(includedir)\Slice\CPlusPlusUtil.h" \
    "$(includedir)\IceUtil\Functional.h" \
    "$(includedir)\IceUtil\Iterator.h" \
    "$(includedir)\Slice\Checksum.h" \
    "$(includedir)\Slice\FileTracker.h" \

Main.obj: \
	Main.cpp \
    "$(includedir)\IceUtil\Options.h" \
    "$(includedir)\IceUtil\Config.h" \
    "$(includedir)\IceUtil\RecMutex.h" \
    "$(includedir)\IceUtil\Lock.h" \
    "$(includedir)\IceUtil\ThreadException.h" \
    "$(includedir)\IceUtil\Exception.h" \
    "$(includedir)\IceUtil\Time.h" \
    "$(includedir)\IceUtil\MutexProtocol.h" \
    "$(includedir)\IceUtil\Shared.h" \
    "$(includedir)\IceUtil\Atomic.h" \
    "$(includedir)\IceUtil\Handle.h" \
    "$(includedir)\IceUtil\CtrlCHandler.h" \
    "$(includedir)\IceUtil\Mutex.h" \
    "$(includedir)\IceUtil\MutexPtrLock.h" \
    "$(includedir)\Slice\Preprocessor.h" \
    "$(includedir)\Slice\FileTracker.h" \
    "$(includedir)\Slice\Parser.h" \
    "$(includedir)\Slice\Util.h" \
    "$(includedir)\IceUtil\OutputUtil.h" \
    "Gen.h" \
