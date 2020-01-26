import csv, sys
from distutils.util import strtobool

if len(sys.argv) != 2:
    exit()

runCount = 0
wonCount = 0
lossCount = 0
runOutOfCubesCount = 0
runOutOfCardsCount = 0
tooManyOutbreakCount = 0

with open(sys.argv[1]) as csvFile:
    reader = csv.reader(csvFile)
    for row in reader:
        runCount += 1
        if strtobool(row[0]):
            wonCount += 1
        else:
            lossCount += 1
            reason = int(row[1])
            if reason == 0:
                runOutOfCardsCount += 1
            elif reason == 1:
                tooManyOutbreakCount += 1
            elif reason == 2:
                runOutOfCubesCount += 1
                

print(f"Sample size: {runCount}")
print(f"Winning: {round((wonCount/runCount)*100, 2)}%\nLosing: {round((lossCount/runCount)*100, 2)}%")
print(f"\tRun out of cards: {round((runOutOfCardsCount/lossCount)*100, 2)}%")
print(f"\tRun out of cubes: {round((runOutOfCubesCount/lossCount)*100, 2)}%")
print(f"\tToo many outbreaks: {round((tooManyOutbreakCount/lossCount)*100, 2)}%")